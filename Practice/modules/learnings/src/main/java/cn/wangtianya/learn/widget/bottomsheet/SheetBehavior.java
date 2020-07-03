package cn.wangtianya.learn.widget.bottomsheet;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.design.R.dimen;
import android.support.design.R.styleable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.math.MathUtils;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class SheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_SETTLING = 2;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_HALF_EXPANDED = 6;
    public static final int PEEK_HEIGHT_AUTO = -1;
    private static final float HIDE_THRESHOLD = 0.5F;
    private static final float HIDE_FRICTION = 0.1F;
    private boolean fitToContents = true;
    private float maximumVelocity;
    private int peekHeight;
    private int mHideHeight;
    private boolean peekHeightAuto;
    private int peekHeightMin;
    private int lastPeekHeight;
    int fitToContentsOffset;
    int halfExpandedOffset;
    int collapsedOffset;
    boolean hideable;
    private boolean skipCollapsed;
    int state = 4;
    ViewDragHelper viewDragHelper;
    private boolean ignoreEvents;
    private int lastNestedScrollDy;
    private boolean nestedScrolled;
    int parentHeight;
    WeakReference<V> viewRef;
    WeakReference<View> nestedScrollingChildRef;
    private SheetBehavior.BottomSheetCallback callback;
    private VelocityTracker velocityTracker;
    int activePointerId;
    private int initialY;
    boolean touchingScrollingChild;
    private Map<View, Integer> importantForAccessibilityMap;
    private final ViewDragHelper.Callback dragCallback;

    public SheetBehavior() {
        this.dragCallback = new NamelessClass_1();
    }

    public SheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.dragCallback = new NamelessClass_1();
        TypedArray a = context.obtainStyledAttributes(attrs, styleable.BottomSheetBehavior_Layout);
        TypedValue value = a.peekValue(styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        if (value != null && value.data == -1) {
            this.setPeekHeight(value.data);
        } else {
            this.setPeekHeight(a.getDimensionPixelSize(styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1));
        }

        this.setHideable(a.getBoolean(styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        this.setFitToContents(a.getBoolean(styleable.BottomSheetBehavior_Layout_behavior_fitToContents, true));
        this.setSkipCollapsed(a.getBoolean(styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        a.recycle();
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.maximumVelocity = (float) configuration.getScaledMaximumFlingVelocity();
    }

    public Parcelable onSaveInstanceState(CoordinatorLayout parent, V child) {
        return new SheetBehavior.SavedState(super.onSaveInstanceState(parent, child), this.state);
    }

    public void onRestoreInstanceState(CoordinatorLayout parent, V child, Parcelable state) {
        SheetBehavior.SavedState ss = (SheetBehavior.SavedState) state;
        super.onRestoreInstanceState(parent, child, ss.getSuperState());
        if (ss.state != 1 && ss.state != 2) {
            this.state = ss.state;
        } else {
            this.state = 4;
        }

    }

    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        if (ViewCompat.getFitsSystemWindows(parent) && !ViewCompat.getFitsSystemWindows(child)) {
            child.setFitsSystemWindows(true);
        }

        int savedTop = child.getTop();
        parent.onLayoutChild(child, layoutDirection);
        this.parentHeight = parent.getHeight() - mHideHeight;
        if (this.peekHeightAuto) {
            if (this.peekHeightMin == 0) {
                this.peekHeightMin = parent.getResources().getDimensionPixelSize(dimen.design_bottom_sheet_peek_height_min);
            }

            this.lastPeekHeight = Math.max(this.peekHeightMin, this.parentHeight - parent.getWidth() * 9 / 16);
        } else {
            this.lastPeekHeight = this.peekHeight;
        }

        this.fitToContentsOffset = Math.max(0, this.parentHeight - child.getHeight());
        this.halfExpandedOffset = this.parentHeight / 2;
        this.calculateCollapsedOffset();
        if (this.state == 3) {
            ViewCompat.offsetTopAndBottom(child, this.getExpandedOffset());
        } else if (this.state == 6) {
            ViewCompat.offsetTopAndBottom(child, this.halfExpandedOffset);
        } else if (this.hideable && this.state == 5) {
            ViewCompat.offsetTopAndBottom(child, this.parentHeight);
        } else if (this.state == 4) {
            ViewCompat.offsetTopAndBottom(child, this.collapsedOffset);
        } else if (this.state == 1 || this.state == 2) {
            ViewCompat.offsetTopAndBottom(child, savedTop - child.getTop());
        }

        if (this.viewDragHelper == null) {
            this.viewDragHelper = ViewDragHelper.create(parent, this.dragCallback);
        }

        this.viewRef = new WeakReference(child);
        this.nestedScrollingChildRef = new WeakReference(this.findScrollingChild(child));
        return true;
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        if (!child.isShown()) {
            this.ignoreEvents = true;
            return false;
        } else {
            int action = event.getActionMasked();
            if (action == 0) {
                this.reset();
            }

            if (this.velocityTracker == null) {
                this.velocityTracker = VelocityTracker.obtain();
            }

            this.velocityTracker.addMovement(event);
            switch (action) {
                case 0:
                    int initialX = (int) event.getX();
                    this.initialY = (int) event.getY();
                    View scroll = this.nestedScrollingChildRef != null ? (View) this.nestedScrollingChildRef.get() : null;
                    if (scroll != null && parent.isPointInChildBounds(scroll, initialX, this.initialY)) {
                        this.activePointerId = event.getPointerId(event.getActionIndex());
                        this.touchingScrollingChild = true;
                    }

                    this.ignoreEvents = this.activePointerId == -1 && !parent.isPointInChildBounds(child, initialX, this.initialY);
                    break;
                case 1:
                case 3:
                    this.touchingScrollingChild = false;
                    this.activePointerId = -1;
                    if (this.ignoreEvents) {
                        this.ignoreEvents = false;
                        return false;
                    }
                case 2:
            }

            if (!this.ignoreEvents && this.viewDragHelper != null && this.viewDragHelper.shouldInterceptTouchEvent(event)) {
                return true;
            } else {
                View scroll = this.nestedScrollingChildRef != null ? (View) this.nestedScrollingChildRef.get() : null;
                return action == 2 && scroll != null && !this.ignoreEvents && this.state != 1 && !parent.isPointInChildBounds(scroll, (int) event.getX(), (int) event.getY()) && this.viewDragHelper != null && Math.abs((float) this.initialY - event.getY()) > (float) this.viewDragHelper.getTouchSlop();
            }
        }
    }

    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        if (!child.isShown()) {
            return false;
        } else {
            int action = event.getActionMasked();
            if (this.state == 1 && action == 0) {
                return true;
            } else {
                if (this.viewDragHelper != null) {
                    this.viewDragHelper.processTouchEvent(event);
                }

                if (action == 0) {
                    this.reset();
                }

                if (this.velocityTracker == null) {
                    this.velocityTracker = VelocityTracker.obtain();
                }

                this.velocityTracker.addMovement(event);
                if (action == 2 && !this.ignoreEvents && Math.abs((float) this.initialY - event.getY()) > (float) this.viewDragHelper.getTouchSlop()) {
                    this.viewDragHelper.captureChildView(child, event.getPointerId(event.getActionIndex()));
                }

                return !this.ignoreEvents;
            }
        }
    }

    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        this.lastNestedScrollDy = 0;
        this.nestedScrolled = false;
        return (axes & 2) != 0;
    }

    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (type != 1) {
            View scrollingChild = (View) this.nestedScrollingChildRef.get();
            if (target == scrollingChild) {
                int currentTop = child.getTop();
                int newTop = currentTop - dy;
                if (dy > 0) {
                    if (newTop < this.getExpandedOffset()) {
                        consumed[1] = currentTop - this.getExpandedOffset();
                        ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                        this.setStateInternal(3);
                    } else {
                        consumed[1] = dy;
                        ViewCompat.offsetTopAndBottom(child, -dy);
                        this.setStateInternal(1);
                    }
                } else if (dy < 0 && !target.canScrollVertically(-1)) {
                    if (newTop > this.collapsedOffset && !this.hideable) {
                        consumed[1] = currentTop - this.collapsedOffset;
                        ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                        this.setStateInternal(4);
                    } else {
                        consumed[1] = dy;
                        ViewCompat.offsetTopAndBottom(child, -dy);
                        this.setStateInternal(1);
                    }
                }

                this.dispatchOnSlide(child.getTop());
                this.lastNestedScrollDy = dy;
                this.nestedScrolled = true;
            }
        }
    }

    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int type) {
        if (child.getTop() == this.getExpandedOffset()) {
            this.setStateInternal(3);
        } else if (target == this.nestedScrollingChildRef.get() && this.nestedScrolled) {
            int top;
            byte targetState;
            if (this.lastNestedScrollDy > 0) {
                top = this.getExpandedOffset();
                targetState = 3;
            } else if (this.hideable && this.shouldHide(child, this.getYVelocity())) {
                top = this.parentHeight;
                targetState = 5;
            } else if (this.lastNestedScrollDy == 0) {
                int currentTop = child.getTop();
                if (this.fitToContents) {
                    if (Math.abs(currentTop - this.fitToContentsOffset) < Math.abs(currentTop - this.collapsedOffset)) {
                        top = this.fitToContentsOffset;
                        targetState = 3;
                    } else {
                        top = this.collapsedOffset;
                        targetState = 4;
                    }
                } else if (currentTop < this.halfExpandedOffset) {
                    if (currentTop < Math.abs(currentTop - this.collapsedOffset)) {
                        top = 0;
                        targetState = 3;
                    } else {
                        top = this.halfExpandedOffset;
                        targetState = 6;
                    }
                } else if (Math.abs(currentTop - this.halfExpandedOffset) < Math.abs(currentTop - this.collapsedOffset)) {
                    top = this.halfExpandedOffset;
                    targetState = 6;
                } else {
                    top = this.collapsedOffset;
                    targetState = 4;
                }
            } else {
                top = this.collapsedOffset;
                targetState = 4;
            }

            if (this.viewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
                this.setStateInternal(2);
                ViewCompat.postOnAnimation(child, new SheetBehavior.SettleRunnable(child, targetState));
            } else {
                this.setStateInternal(targetState);
            }

            this.nestedScrolled = false;
        }
    }

    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, float velocityX, float velocityY) {
        return target == this.nestedScrollingChildRef.get() && (this.state != 3 || super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY));
    }

    public boolean isFitToContents() {
        return this.fitToContents;
    }

    public void setFitToContents(boolean fitToContents) {
        if (this.fitToContents != fitToContents) {
            this.fitToContents = fitToContents;
            if (this.viewRef != null) {
                this.calculateCollapsedOffset();
            }

            this.setStateInternal(this.fitToContents && this.state == 6 ? 3 : this.state);
        }
    }

    public final void setPeekHeight(int peekHeight) {
        boolean layout = false;
        if (peekHeight == -1) {
            if (!this.peekHeightAuto) {
                this.peekHeightAuto = true;
                layout = true;
            }
        } else if (this.peekHeightAuto || this.peekHeight != peekHeight) {
            this.peekHeightAuto = false;
            this.peekHeight = Math.max(0, peekHeight);
            this.collapsedOffset = this.parentHeight - peekHeight;
            layout = true;
        }

        if (layout && this.state == 4 && this.viewRef != null) {
            V view = this.viewRef.get();
            if (view != null) {
                view.requestLayout();
            }
        }

    }

    public final int getPeekHeight() {
        return this.peekHeightAuto ? -1 : this.peekHeight;
    }

    public final void setHideHeight(int hideHeight) {
        mHideHeight = hideHeight;
        if (viewRef != null) {
            V view = viewRef.get();
            if (view != null) {
                view.requestLayout();
            }
        }
    }

    public final int getHideHeight() {
        return mHideHeight;
    }

    public void setHideable(boolean hideable) {
        this.hideable = hideable;
    }

    public boolean isHideable() {
        return this.hideable;
    }

    public void setSkipCollapsed(boolean skipCollapsed) {
        this.skipCollapsed = skipCollapsed;
    }

    public boolean getSkipCollapsed() {
        return this.skipCollapsed;
    }

    public void setBottomSheetCallback(SheetBehavior.BottomSheetCallback callback) {
        this.callback = callback;
    }

    public final void setState(final int state) {
        if (state != this.state) {
            if (this.viewRef == null) {
                if (state == 4 || state == 3 || state == 6 || this.hideable && state == 5) {
                    this.state = state;
                }

            } else {
                final V child = this.viewRef.get();
                if (child != null) {
                    ViewParent parent = child.getParent();
                    if (parent != null && parent.isLayoutRequested() && ViewCompat.isAttachedToWindow(child)) {
                        child.post(new Runnable() {
                            public void run() {
                                SheetBehavior.this.startSettlingAnimation(child, state);
                            }
                        });
                    } else {
                        this.startSettlingAnimation(child, state);
                    }

                }
            }
        }
    }

    public final int getState() {
        return this.state;
    }

    void setStateInternal(int state) {
        if (this.state != state) {
            this.state = state;
            if (state != 6 && state != 3) {
                if (state == 5 || state == 4) {
                    this.updateImportantForAccessibility(false);
                }
            } else {
                this.updateImportantForAccessibility(true);
            }

            View bottomSheet = (View) this.viewRef.get();
            if (bottomSheet != null && this.callback != null) {
                this.callback.onStateChanged(bottomSheet, state);
            }

        }
    }

    private void calculateCollapsedOffset() {
        if (this.fitToContents) {
            this.collapsedOffset = Math.max(this.parentHeight - this.lastPeekHeight, this.fitToContentsOffset);
        } else {
            this.collapsedOffset = this.parentHeight - this.lastPeekHeight;
        }

    }

    private void reset() {
        this.activePointerId = -1;
        if (this.velocityTracker != null) {
            this.velocityTracker.recycle();
            this.velocityTracker = null;
        }

    }

    boolean shouldHide(View child, float yvel) {
        if (this.skipCollapsed) {
            return true;
        } else if (child.getTop() < this.collapsedOffset) {
            return false;
        } else {
            float newTop = (float) child.getTop() + yvel * 0.1F;
            return Math.abs(newTop - (float) this.collapsedOffset) / (float) this.peekHeight > 0.5F;
        }
    }

    @VisibleForTesting
    View findScrollingChild(View view) {
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        } else {
            if (view instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) view;
                int i = 0;

                for (int count = group.getChildCount(); i < count; ++i) {
                    View scrollingChild = this.findScrollingChild(group.getChildAt(i));
                    if (scrollingChild != null) {
                        return scrollingChild;
                    }
                }
            }

            return null;
        }
    }

    private float getYVelocity() {
        if (this.velocityTracker == null) {
            return 0.0F;
        } else {
            this.velocityTracker.computeCurrentVelocity(1000, this.maximumVelocity);
            return this.velocityTracker.getYVelocity(this.activePointerId);
        }
    }

    private int getExpandedOffset() {
        return this.fitToContents ? this.fitToContentsOffset : 0;
    }

    void startSettlingAnimation(View child, int state) {
        int top;
        if (state == 4) {
            top = this.collapsedOffset;
        } else if (state == 6) {
            top = this.halfExpandedOffset;
            if (this.fitToContents && top <= this.fitToContentsOffset) {
                state = 3;
                top = this.fitToContentsOffset;
            }
        } else if (state == 3) {
            top = this.getExpandedOffset();
        } else {
            if (!this.hideable || state != 5) {
                throw new IllegalArgumentException("Illegal state argument: " + state);
            }

            top = this.parentHeight;
        }

        if (this.viewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
            this.setStateInternal(2);
            ViewCompat.postOnAnimation(child, new SheetBehavior.SettleRunnable(child, state));
        } else {
            this.setStateInternal(state);
        }

    }

    void dispatchOnSlide(int top) {
        View bottomSheet = (View) this.viewRef.get();
        if (bottomSheet != null && this.callback != null) {
            if (top > this.collapsedOffset) {
                this.callback.onSlide(bottomSheet, (float) (this.collapsedOffset - top) / (float) (this.parentHeight - this.collapsedOffset));
            } else {
                this.callback.onSlide(bottomSheet, (float) (this.collapsedOffset - top) / (float) (this.collapsedOffset - this.getExpandedOffset()));
            }
        }

    }

    @VisibleForTesting
    int getPeekHeightMin() {
        return this.peekHeightMin;
    }

    public static <V extends View> SheetBehavior<V> from(V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof android.support.design.widget.CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        } else {
            CoordinatorLayout.Behavior behavior = ((android.support.design.widget.CoordinatorLayout.LayoutParams) params).getBehavior();
            if (!(behavior instanceof SheetBehavior)) {
                throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
            } else {
                return (SheetBehavior) behavior;
            }
        }
    }

    private void updateImportantForAccessibility(boolean expanded) {
        if (this.viewRef != null) {
            ViewParent viewParent = ((View) this.viewRef.get()).getParent();
            if (viewParent instanceof CoordinatorLayout) {
                CoordinatorLayout parent = (CoordinatorLayout) viewParent;
                int childCount = parent.getChildCount();
                if (Build.VERSION.SDK_INT >= 16 && expanded) {
                    if (this.importantForAccessibilityMap != null) {
                        return;
                    }

                    this.importantForAccessibilityMap = new HashMap(childCount);
                }

                for (int i = 0; i < childCount; ++i) {
                    View child = parent.getChildAt(i);
                    if (child != this.viewRef.get()) {
                        if (!expanded) {
                            if (this.importantForAccessibilityMap != null && this.importantForAccessibilityMap.containsKey(child)) {
                                ViewCompat.setImportantForAccessibility(child, (Integer) this.importantForAccessibilityMap.get(child));
                            }
                        } else {
                            if (Build.VERSION.SDK_INT >= 16) {
                                this.importantForAccessibilityMap.put(child, child.getImportantForAccessibility());
                            }

                            ViewCompat.setImportantForAccessibility(child, 4);
                        }
                    }
                }

                if (!expanded) {
                    this.importantForAccessibilityMap = null;
                }

            }
        }
    }

    protected static class SavedState extends AbsSavedState {
        final int state;
        public static final Creator<SheetBehavior.SavedState> CREATOR = new ClassLoaderCreator<SheetBehavior.SavedState>() {
            public SheetBehavior.SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SheetBehavior.SavedState(in, loader);
            }

            public SheetBehavior.SavedState createFromParcel(Parcel in) {
                return new SheetBehavior.SavedState(in, (ClassLoader) null);
            }

            public SheetBehavior.SavedState[] newArray(int size) {
                return new SheetBehavior.SavedState[size];
            }
        };

        public SavedState(Parcel source) {
            this(source, (ClassLoader) null);
        }

        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            this.state = source.readInt();
        }

        public SavedState(Parcelable superState, int state) {
            super(superState);
            this.state = state;
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.state);
        }
    }

    private class SettleRunnable implements Runnable {
        private final View view;
        private final int targetState;

        SettleRunnable(View view, int targetState) {
            this.view = view;
            this.targetState = targetState;
        }

        public void run() {
            if (SheetBehavior.this.viewDragHelper != null && SheetBehavior.this.viewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.view, this);
            } else {
                SheetBehavior.this.setStateInternal(this.targetState);
            }

        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface State {
    }

    public abstract static class BottomSheetCallback {
        public BottomSheetCallback() {
        }

        public abstract void onStateChanged(@NonNull View var1, int var2);

        public abstract void onSlide(@NonNull View var1, float var2);
    }


    class NamelessClass_1 extends ViewDragHelper.Callback {
        NamelessClass_1() {
        }

        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            if (SheetBehavior.this.state == 1) {
                return false;
            } else if (SheetBehavior.this.touchingScrollingChild) {
                return false;
            } else {
                if (SheetBehavior.this.state == 3 && SheetBehavior.this.activePointerId == pointerId) {
                    View scroll = (View) SheetBehavior.this.nestedScrollingChildRef.get();
                    if (scroll != null && scroll.canScrollVertically(-1)) {
                        return false;
                    }
                }

                return SheetBehavior.this.viewRef != null && SheetBehavior.this.viewRef.get() == child;
            }
        }

        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            SheetBehavior.this.dispatchOnSlide(top);
        }

        public void onViewDragStateChanged(int state) {
            if (state == 1) {
                SheetBehavior.this.setStateInternal(1);
            }

        }

        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            int top;
            byte targetState;
            int currentTop;
            if (yvel < 0.0F) {
                if (SheetBehavior.this.fitToContents) {
                    top = SheetBehavior.this.fitToContentsOffset;
                    targetState = 3;
                } else {
                    currentTop = releasedChild.getTop();
                    if (currentTop > SheetBehavior.this.halfExpandedOffset) {
                        top = SheetBehavior.this.halfExpandedOffset;
                        targetState = 6;
                    } else {
                        top = 0;
                        targetState = 3;
                    }
                }
            } else if (!SheetBehavior.this.hideable || !SheetBehavior.this.shouldHide(releasedChild, yvel) || releasedChild.getTop() <= SheetBehavior.this.collapsedOffset && Math.abs(xvel) >= Math.abs(yvel)) {
                if (yvel != 0.0F && Math.abs(xvel) <= Math.abs(yvel)) {
                    top = SheetBehavior.this.collapsedOffset;
                    targetState = 4;
                } else {
                    currentTop = releasedChild.getTop();
                    if (SheetBehavior.this.fitToContents) {
                        if (Math.abs(currentTop - SheetBehavior.this.fitToContentsOffset) < Math.abs(currentTop - SheetBehavior.this.collapsedOffset)) {
                            top = SheetBehavior.this.fitToContentsOffset;
                            targetState = 3;
                        } else {
                            top = SheetBehavior.this.collapsedOffset;
                            targetState = 4;
                        }
                    } else if (currentTop < SheetBehavior.this.halfExpandedOffset) {
                        if (currentTop < Math.abs(currentTop - SheetBehavior.this.collapsedOffset)) {
                            top = 0;
                            targetState = 3;
                        } else {
                            top = SheetBehavior.this.halfExpandedOffset;
                            targetState = 6;
                        }
                    } else if (Math.abs(currentTop - SheetBehavior.this.halfExpandedOffset) < Math.abs(currentTop - SheetBehavior.this.collapsedOffset)) {
                        top = SheetBehavior.this.halfExpandedOffset;
                        targetState = 6;
                    } else {
                        top = SheetBehavior.this.collapsedOffset;
                        targetState = 4;
                    }
                }
            } else {
                top = SheetBehavior.this.parentHeight;
                targetState = 5;
            }

            if (SheetBehavior.this.viewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top)) {
                SheetBehavior.this.setStateInternal(2);
                ViewCompat.postOnAnimation(releasedChild, SheetBehavior.this.new SettleRunnable(releasedChild, targetState));
            } else {
                SheetBehavior.this.setStateInternal(targetState);
            }

        }

        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return MathUtils.clamp(top, SheetBehavior.this.getExpandedOffset(), SheetBehavior.this.hideable ? SheetBehavior.this.parentHeight : SheetBehavior.this.collapsedOffset);
        }

        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return child.getLeft();
        }

        public int getViewVerticalDragRange(@NonNull View child) {
            return SheetBehavior.this.hideable ? SheetBehavior.this.parentHeight : SheetBehavior.this.collapsedOffset;
        }
    }
}
