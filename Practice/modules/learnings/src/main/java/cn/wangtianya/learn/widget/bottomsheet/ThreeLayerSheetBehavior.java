package cn.wangtianya.learn.widget.bottomsheet;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.math.MathUtils;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

import static android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP;


/**
 * http://www.apache.org/licenses/LICENSE-2.0
 * Copy from google material repo.
 * https://github.com/material-components/material-components-android/blob/master/lib/java/com/google/android/material/bottomsheet/BottomSheetBehavior.java
 */
public class ThreeLayerSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    public abstract static class BottomSheetCallback {
        public abstract void onStateChanged(@NonNull View bottomSheet, @ThreeLayerSheetBehavior.State int newState);

        public abstract void onSlide(@NonNull View bottomSheet, float slideOffset);
    }

    public static final int STATE_DRAGGING = 1;
    public static final int STATE_SETTLING = 2;

    public static final int STATE_EXPANDED = 3;

    public static final int STATE_COLLAPSED = 4;

    public static final int STATE_HIDDEN = 5;

    @RestrictTo(LIBRARY_GROUP)
    @IntDef({STATE_EXPANDED, STATE_COLLAPSED, STATE_DRAGGING, STATE_SETTLING, STATE_HIDDEN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public static final int PEEK_HEIGHT_AUTO = -1;

    private int mPeekHeight;

    private int mHideHeight;

    private boolean mPeekHeightAuto;

    private int mPeekHeightMin;

    private int mMinOffset;

    private int mMaxOffset;

    private boolean mHideable;

    private boolean mDraggable;

    private boolean mSkipCollapsed;

    @ThreeLayerSheetBehavior.State
    private int mState = STATE_COLLAPSED;

    private ViewDragHelper mViewDragHelper;

    private boolean mIgnoreEvents;

    private int mLastNestedScrollDy;

    private boolean mNestedScrolled;

    private int mParentHeight;

    private WeakReference<V> mViewRef;

    private WeakReference<View> mNestedScrollingChildRef;

    private ThreeLayerSheetBehavior.BottomSheetCallback mCallback;

    private VelocityTracker mVelocityTracker;

    private int mActivePointerId;

    private int mInitialY;

    private boolean mTouchingScrollingChild;


    private int mTouchSlop;

    public ThreeLayerSheetBehavior() {
    }

    public ThreeLayerSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    public Parcelable onSaveInstanceState(CoordinatorLayout parent, V child) {
        return new ThreeLayerSheetBehavior.SavedState(super.onSaveInstanceState(parent, child), mState);
    }

    @Override
    public void onRestoreInstanceState(CoordinatorLayout parent, V child, Parcelable state) {
        ThreeLayerSheetBehavior.SavedState ss = (ThreeLayerSheetBehavior.SavedState) state;
        super.onRestoreInstanceState(parent, child, ss.getSuperState());
        // Intermediate states are restored as collapsed state
        if (ss.state == STATE_DRAGGING || ss.state == STATE_SETTLING) {
            mState = STATE_COLLAPSED;
        } else {
            mState = ss.state;
        }
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        if (ViewCompat.getFitsSystemWindows(parent) && !ViewCompat.getFitsSystemWindows(child)) {
            ViewCompat.setFitsSystemWindows(child, true);
        }
        int savedTop = child.getTop();
        // First let the parent lay it out
        parent.onLayoutChild(child, layoutDirection);
        // Offset the bottom sheet
        mParentHeight = parent.getHeight() - mHideHeight;
        int peekHeight;
        if (mPeekHeightAuto) {
            if (mPeekHeightMin == 0) {
                mPeekHeightMin = 100;
            }
            peekHeight = Math.max(mPeekHeightMin, mParentHeight - parent.getWidth() * 9 / 16);
        } else {
            peekHeight = mPeekHeight;
        }
        mMinOffset = Math.max(0, mParentHeight - child.getHeight());
        mMaxOffset = Math.max(mParentHeight - peekHeight, mMinOffset);
        if (mState == STATE_EXPANDED) {
            ViewCompat.offsetTopAndBottom(child, mMinOffset);
        } else if (mHideable && mState == STATE_HIDDEN) {
            ViewCompat.offsetTopAndBottom(child, mParentHeight);
        } else if (mState == STATE_COLLAPSED) {
            ViewCompat.offsetTopAndBottom(child, mMaxOffset);
        } else if (mState == STATE_DRAGGING || mState == STATE_SETTLING) {
            ViewCompat.offsetTopAndBottom(child, savedTop - child.getTop());
        }
        if (mViewDragHelper == null) {
            mViewDragHelper = ViewDragHelper.create(parent, mDragCallback);
        }
        mViewRef = new WeakReference<>(child);
        mNestedScrollingChildRef = new WeakReference<>(findScrollingChild(child));
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        if (!child.isShown()) {
            mIgnoreEvents = true;
            return false;
        }
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) { // Record the velocity
            reset();
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        switch (action) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mTouchingScrollingChild = false;
                mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                if (mIgnoreEvents) { // Reset the ignore flag
                    mIgnoreEvents = false;
                    return false;
                }
                break;
            case MotionEvent.ACTION_DOWN:
                int initialX = (int) event.getX();
                mInitialY = (int) event.getY();
                View scroll = mNestedScrollingChildRef != null
                        ? mNestedScrollingChildRef.get() : null;
                if (scroll != null && parent.isPointInChildBounds(scroll, initialX, mInitialY)) {
                    mActivePointerId = event.getPointerId(event.getActionIndex());
                    mTouchingScrollingChild = true;
                }
                mIgnoreEvents = mActivePointerId == MotionEvent.INVALID_POINTER_ID &&
                        !parent.isPointInChildBounds(child, initialX, mInitialY);
                break;
        }
        if (!mIgnoreEvents && mViewDragHelper.shouldInterceptTouchEvent(event)) {
            return true;
        }
        // We have to handle cases that the ViewDragHelper does not capture the bottom sheet because
        // it is not the top most view of its parent. This is not necessary when the touch event is
        // happening over the scrolling content as nested scrolling logic handles that case.
        View scroll = null;

        if (mNestedScrollingChildRef != null) {
            scroll = mNestedScrollingChildRef.get();
        }

        return action == MotionEvent.ACTION_MOVE && scroll != null &&
                !mIgnoreEvents && mState != STATE_DRAGGING &&
                !parent.isPointInChildBounds(scroll, (int) event.getX(), (int) event.getY()) &&
                Math.abs(mInitialY - event.getY()) > mViewDragHelper.getTouchSlop();
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        if (!child.isShown()) {
            return false;
        }
        int action = event.getActionMasked();
        if (mState == STATE_DRAGGING && action == MotionEvent.ACTION_DOWN) {
            return true;
        }
        if (mViewDragHelper != null) {
            mViewDragHelper.processTouchEvent(event);
        }
        if (action == MotionEvent.ACTION_DOWN) {        // Record the velocity

            reset();
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        // The ViewDragHelper tries to capture only the top-most View. We have to explicitly tell it
        // to capture the bottom sheet in case it is not captured and the touch slop is passed.
        if (action == MotionEvent.ACTION_MOVE && !mIgnoreEvents && mViewDragHelper != null) {
            if (Math.abs(mInitialY - event.getY()) > mViewDragHelper.getTouchSlop()) {
                mViewDragHelper.captureChildView(child, event.getPointerId(event.getActionIndex()));
            }
        }
        return !mIgnoreEvents;
    }

    private WeakReference<View> forceScrolledChild = null;

    public void setForceScrolledChild(View scroller) {
        forceScrolledChild = new WeakReference<>(scroller);
    }

    private int totalScrollDy = 0;

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        mLastNestedScrollDy = 0;
        totalScrollDy = 0;
        mNestedScrolled = false;
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V child, View target, int dx,
                                  int dy, int[] consumed) {

        View scrollingChild = mNestedScrollingChildRef.get();
        if (target != scrollingChild) {
            return;
        }

        totalScrollDy += dy;
        if (Math.abs(totalScrollDy) <= mTouchSlop) {
            consumed[1] = dy;
            return;
        }


        int currentTop = child.getTop();
        int newTop = currentTop - dy;
        // 扩展滑动阈值
        if (dy > 0) { // Upward
            if (newTop < mMinOffset) {
                consumed[1] = currentTop - mMinOffset;
                ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                setStateInternal(STATE_EXPANDED);
            } else {
                consumed[1] = dy;
                ViewCompat.offsetTopAndBottom(child, -dy);
                setStateInternal(STATE_DRAGGING);
            }
        } else if (dy < -0) { // Downward
            if (!target.canScrollVertically(-1)) {
                float slideOffSet = Math.abs((float)
                        (mMaxOffset - child.getTop()) / (mParentHeight - mMaxOffset));
                if (slideOffSet >= 1) { // 超出范围不处理，防止嵌套时
                    ViewCompat.offsetTopAndBottom(child, 0);
                    setStateInternal(STATE_HIDDEN);
                    if (slideOffSet > 1) {
                        return;
                    }
                } else if (newTop <= mMaxOffset || mHideable) {
                    consumed[1] = dy;
                    ViewCompat.offsetTopAndBottom(child, -dy);
                    setStateInternal(STATE_DRAGGING);
                } else {
                    consumed[1] = currentTop - mMaxOffset;
                    ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                    setStateInternal(STATE_COLLAPSED);
                }
            }
        }
        dispatchOnSlide(child.getTop());
        if (Math.abs(dy) > 10) {
            mLastNestedScrollDy = dy;
        }
        mNestedScrolled = true;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V child, View target) {
        if (child.getTop() == mMinOffset) {
            setStateInternal(STATE_EXPANDED);
            return;
        }
        if (mNestedScrollingChildRef == null || target != mNestedScrollingChildRef.get()
                || !mNestedScrolled) {
            return;
        }
        int currentTop = child.getTop();

        int top;
        int targetState;
        int lastDragY = -1 * mLastNestedScrollDy;

        if (customDragReleaseCallback != null) {
            targetState = customDragReleaseCallback.onViewReleased(
                    lastDragY, currentTop, mMinOffset, mMaxOffset, mParentHeight);
        } else {
            targetState = defaultDragReleaseCallback.onViewReleased(
                    lastDragY, currentTop, mMinOffset, mMaxOffset, mParentHeight);
        }

        top = calculateStateHeight(targetState);

        if (mViewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
            setStateInternal(STATE_SETTLING);
            ViewCompat.postOnAnimation(child, new ThreeLayerSheetBehavior.SettleRunnable(child, targetState));
        } else {
            setStateInternal(targetState);
        }
        mNestedScrolled = false;
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V child, View target,
                                    float velocityX, float velocityY) {
        return target == mNestedScrollingChildRef.get() &&
                (mState != STATE_EXPANDED ||
                        super.onNestedPreFling(coordinatorLayout, child, target,
                                velocityX, velocityY));
    }

    public final void setPeekHeight(int peekHeight) {
        boolean layout = false;
        if (peekHeight == PEEK_HEIGHT_AUTO) {
            if (!mPeekHeightAuto) {
                mPeekHeightAuto = true;
                layout = true;
            }
        } else if (mPeekHeightAuto || mPeekHeight != peekHeight) {
            mPeekHeightAuto = false;
            mPeekHeight = Math.max(0, peekHeight);
            mMaxOffset = mParentHeight - peekHeight;
            layout = true;
        }
        if (layout && mState == STATE_COLLAPSED && mViewRef != null) {
            V view = mViewRef.get();
            if (view != null) {
                view.requestLayout();
            }
        }
    }

    public final int getPeekHeight() {
        return mPeekHeightAuto ? PEEK_HEIGHT_AUTO : mPeekHeight;
    }

    public final void setHideHeight(int hideHeight) {
        mHideHeight = hideHeight;
        if (mViewRef != null) {
            V view = mViewRef.get();
            if (view != null) {
                view.requestLayout();
            }
        }
    }

    public final int getHideHeight() {
        return mHideHeight;
    }


    public void setHideable(boolean hideable) {
        mHideable = hideable;
    }

    public void setDraggable(boolean draggable) {
        mDraggable = draggable;
    }


    public boolean isHideable() {
        return mHideable;
    }

    public void setSkipCollapsed(boolean skipCollapsed) {
        mSkipCollapsed = skipCollapsed;
    }

    public boolean getSkipCollapsed() {
        return mSkipCollapsed;
    }

    public void setBottomSheetCallback(ThreeLayerSheetBehavior.BottomSheetCallback callback) {
        mCallback = callback;
    }

    public final void setState(final @ThreeLayerSheetBehavior.State int state, final boolean anim) {
        if (state == mState) {
            return;
        }
        if (mViewRef == null) {
            // The view is not laid out yet; modify mState and let onLayoutChild handle it later
            if (state == STATE_COLLAPSED || state == STATE_EXPANDED ||
                    (mHideable && state == STATE_HIDDEN)) {
                mState = state;
            }
            return;
        }
        final V child = mViewRef.get();
        if (child == null) {
            return;
        }
        // Start the animation; wait until a pending layout if there is one.
        ViewParent parent = child.getParent();
        if (parent != null && parent.isLayoutRequested() && ViewCompat.isAttachedToWindow(child) && anim) {
            child.post(new Runnable() {
                @Override
                public void run() {
                    startSettlingAnimation(child, state, anim);
                }
            });
        } else {
            startSettlingAnimation(child, state, anim);
        }
    }

    public final void setState(final @ThreeLayerSheetBehavior.State int state) {
        setState(state, true);
    }

    @ThreeLayerSheetBehavior.State
    public final int getState() {
        return mState;
    }

    void setStateInternal(@ThreeLayerSheetBehavior.State int state) {
        if (mState == state) {
            return;
        }
        mState = state;
        View bottomSheet = mViewRef.get();
        if (bottomSheet != null && mCallback != null) {
            mCallback.onStateChanged(bottomSheet, state);
        }
    }

    private void reset() {
        mActivePointerId = ViewDragHelper.INVALID_POINTER;
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }


    @VisibleForTesting
    private View findScrollingChild(View view) {
        if (forceScrolledChild != null && forceScrolledChild.get() != null) {
            return forceScrolledChild.get();
        }

        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0, count = group.getChildCount(); i < count; i++) {
                View scrollingChild = findScrollingChild(group.getChildAt(i));
                if (scrollingChild != null) {
                    return scrollingChild;
                }
            }
        }
        return null;
    }


    private void startSettlingAnimation(View child, int state, boolean anim) {
        int top;
        if (state == STATE_COLLAPSED) {
            top = mMaxOffset;
        } else if (state == STATE_EXPANDED) {
            top = mMinOffset;
        } else if (mHideable && state == STATE_HIDDEN) {
            top = mParentHeight;
        } else {
            // default impl has problem ，post will cause mHideable STATE_HIDDEN delay, and go into this branch
            return;
        }

        if (!anim) {
            int dy = top - child.getTop();
            ViewCompat.offsetTopAndBottom(child, dy);
            setStateInternal(state);
            dispatchOnSlide(top);
        } else if (mViewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
            setStateInternal(STATE_SETTLING);
            ViewCompat.postOnAnimation(child, new ThreeLayerSheetBehavior.SettleRunnable(child, state));
        } else {
            setStateInternal(state);
        }
    }

    private final ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback() {

        private int lastDragDy = 0;

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (mState == STATE_DRAGGING) {
                return false;
            }
            if (mTouchingScrollingChild) {
                return false;
            }
            if (mState == STATE_EXPANDED && mActivePointerId == pointerId) {
                View scroll = mNestedScrollingChildRef.get();
                if (scroll != null && scroll.canScrollVertically(-1)) {
                    return false;   // Let the content scroll up
                }
            }
            return mViewRef != null && mViewRef.get() == child;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            dispatchOnSlide(top);
            if (Math.abs(dy) > 5) {
                lastDragDy = dy;
            }
        }

        @Override
        public void onViewDragStateChanged(int state) {
            if (state == ViewDragHelper.STATE_DRAGGING) {
                setStateInternal(STATE_DRAGGING);
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            final int currentTop = releasedChild.getTop();

            int top;
            @ThreeLayerSheetBehavior.State int targetState;

            if (customDragReleaseCallback != null) {
                targetState = customDragReleaseCallback.onViewReleased(
                        lastDragDy, currentTop, mMinOffset, mMaxOffset, mParentHeight);
            } else {
                targetState = defaultDragReleaseCallback.onViewReleased(
                        lastDragDy, currentTop, mMinOffset, mMaxOffset, mParentHeight);
            }

            top = calculateStateHeight(targetState);

            if (mViewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top)) {
                setStateInternal(STATE_SETTLING);
                ViewCompat.postOnAnimation(releasedChild,
                        new ThreeLayerSheetBehavior.SettleRunnable(releasedChild, targetState));
            } else {
                setStateInternal(targetState);
            }
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return MathUtils.clamp(top, mMinOffset, mHideable ? mParentHeight : mMaxOffset);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return child.getLeft();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            if (!mDraggable) {
                return 0;
            } else if (mHideable) {
                return mParentHeight - mMinOffset;
            } else {
                return mMaxOffset - mMinOffset;
            }
        }
    };

    void dispatchOnSlide(int top) {
        View bottomSheet = mViewRef.get();
        if (bottomSheet != null && mCallback != null) {
            if (top > mMaxOffset) {
                mCallback.onSlide(bottomSheet, (float) (mMaxOffset - top) /
                        (mParentHeight - mMaxOffset));
            } else {
                mCallback.onSlide(bottomSheet,
                        (float) (mMaxOffset - top) / ((mMaxOffset - mMinOffset)));
            }
        }
    }

    @VisibleForTesting
    int getPeekHeightMin() {
        return mPeekHeightMin;
    }

    private class SettleRunnable implements Runnable {

        private final View mView;

        @ThreeLayerSheetBehavior.State
        private final int mTargetState;

        SettleRunnable(View view, @ThreeLayerSheetBehavior.State int targetState) {
            mView = view;
            mTargetState = targetState;
        }

        @Override
        public void run() {
            if (mViewDragHelper != null && mViewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(mView, this);
            } else {
                setStateInternal(mTargetState);
            }
        }
    }

    protected static class SavedState extends AbsSavedState {
        @ThreeLayerSheetBehavior.State
        final int state;

        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            state = source.readInt();
        }

        public SavedState(Parcelable superState, @ThreeLayerSheetBehavior.State int state) {
            super(superState);
            this.state = state;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(state);
        }

        public static final Creator<ThreeLayerSheetBehavior.SavedState> CREATOR = new ClassLoaderCreator<ThreeLayerSheetBehavior.SavedState>() {
            @Override
            public ThreeLayerSheetBehavior.SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new ThreeLayerSheetBehavior.SavedState(in, loader);
            }

            @Override
            public ThreeLayerSheetBehavior.SavedState createFromParcel(Parcel in) {
                return new ThreeLayerSheetBehavior.SavedState(in, null);
            }

            @Override
            public ThreeLayerSheetBehavior.SavedState[] newArray(int size) {
                return new ThreeLayerSheetBehavior.SavedState[size];
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <V extends View> ThreeLayerSheetBehavior<V> from(V view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params)
                .getBehavior();
        if (!(behavior instanceof ThreeLayerSheetBehavior)) {
            throw new IllegalArgumentException(
                    "The view is not associated with BottomSheetBehavior");
        }
        return (ThreeLayerSheetBehavior<V>) behavior;
    }


    private int nearestState(int top) {
        if (top < (mMaxOffset + mMinOffset) / 2) {
            return STATE_EXPANDED;
        } else if (top < (mMaxOffset + mParentHeight) / 2) {
            return STATE_COLLAPSED;
        } else {
            return STATE_HIDDEN;
        }
    }

    private int upNearestState(int top) {
        if (top < mMinOffset) {
            return STATE_EXPANDED;
        } else if (top < mMaxOffset) {
            return STATE_COLLAPSED;
        } else {
            return STATE_HIDDEN;
        }
    }

    private int downNearestState(int top) {
        if (top > mParentHeight) {
            return STATE_HIDDEN;
        } else if (top > mMaxOffset) {
            return STATE_COLLAPSED;
        } else {
            return STATE_EXPANDED;
        }
    }

    private int calculateStateHeight(int state) {
        switch (state) {
            case STATE_EXPANDED:
                return mMinOffset;
            case STATE_COLLAPSED:
                return mMaxOffset;
            case STATE_HIDDEN:
                return mParentHeight;
            default:
                return mParentHeight;
        }
    }

    public interface DragReleaseCallback {
        @ThreeLayerSheetBehavior.State
        int onViewReleased(int lastDragY, int currentTop,
                           int expandHeight, int collapsedHeight,
                           int hideHeight);
    }

    private class DefaultDragReleaseCallback implements DragReleaseCallback {

        @Override
        public int onViewReleased(int lastDragY, int currentTop,
                                  int expandHeight, int collapsedHeight,
                                  int hideHeight) {
            @ThreeLayerSheetBehavior.State int targetState;
            if (lastDragY < 0) {
                // up judge
                targetState = upNearestState(currentTop) - 1;
                if (targetState < STATE_EXPANDED) {
                    targetState = STATE_EXPANDED;
                }
            } else if (lastDragY > 0) {
                // down judge
                targetState = downNearestState(currentTop) + 1;
                if (targetState > STATE_HIDDEN) {
                    targetState = STATE_HIDDEN;
                }
            } else {
                // no speed judge
                targetState = nearestState(currentTop);
            }
            return targetState;
        }
    }

    private DragReleaseCallback defaultDragReleaseCallback = new DefaultDragReleaseCallback();

    private DragReleaseCallback customDragReleaseCallback;

    public void setCustomDragReleaseCallback(DragReleaseCallback customDragReleaseCallback) {
        this.customDragReleaseCallback = customDragReleaseCallback;
    }
}
