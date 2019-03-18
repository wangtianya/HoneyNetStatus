package com.wedcel.dragexpandgrid.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import cn.wangtianya.practice.lib.draggrid.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CustomBehindView extends GridView {
    /*** DragGridView的item长按响应的时间， 默认是1000毫秒，也可以自行设置 **/
    private long dragResponseMS = 100;
    /**
     * 是否可以拖拽，默认不可以
     **/
    private boolean isDrag = false;

    private int mDownX;
    private int mDownY;
    private int moveX;
    private int moveY;
    /**
     * 正在拖拽的position
     **/
    private int mDragPosition;
    /*** 刚开始拖拽的item对应的View **/
    private View mStartDragItemView = null;
    /**
     * 用于拖拽的镜像，这里直接用一个ImageView
     **/
    private ImageView mDragImageView;
    private WindowManager mWindowManager;
    /**
     * item镜像的布局参数
     **/
    private WindowManager.LayoutParams mWindowLayoutParams;
    /**
     * 我们拖拽的item对应的Bitmap
     **/
    private Bitmap mDragBitmap;
    /**
     * 按下的点到所在item的上边缘的距离
     **/
    private int mPoint2ItemTop;
    /**
     * 按下的点到所在item的左边缘的距离
     **/
    private int mPoint2ItemLeft;
    /**
     * DragGridView距离屏幕顶部的偏移量
     **/
    private int mOffset2Top;
    /**
     * DragGridView距离屏幕左边的偏移量
     **/
    private int mOffset2Left;
    /**
     * 状态栏的高度
     **/
    private int mStatusHeight;
    /**
     * DragGridView自动向下滚动的边界值
     **/
    private int mDownScrollBorder;
    /**
     * DragGridView自动向上滚动的边界值
     **/
    private int mUpScrollBorder;
    /**
     * DragGridView自动滚动的速度
     **/
    private static final int speed = 20;

    private boolean mAnimationEnd = true;

    private int mNumColumns = AUTO_FIT;
    private DragGridAdapter mDragAdapter;
    private ArrayList<DragIconInfo> mIconInfoList = new ArrayList<DragIconInfo>();
    private CustomGroup mCustomGroup;
    private Context mContext;

    public CustomBehindView(Context context, CustomGroup customGroup) {
        super(context);
        this.mContext = context;
        this.mCustomGroup = customGroup;
        this.setNumColumns(CustomGroup.COLUMNUM);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mStatusHeight = getStatusHeight(context); // 获取状态栏的高度
    }

    private Handler mHandler = new Handler();

    private Runnable mLongClickRunnable = new Runnable() {

        @Override
        public void run() {
            isDrag = true;
            mStartDragItemView.setVisibility(View.INVISIBLE);
            createDragImage(mDragBitmap, mDownX, mDownY);
        }
    };

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);

        if (adapter instanceof DragGridAdapter) {
            mDragAdapter = (DragGridAdapter) adapter;
        } else {
            throw new IllegalStateException("the adapter must be implements DragGridAdapter");
        }
    }

    @Override
    public void setNumColumns(int numColumns) {
        super.setNumColumns(numColumns);
        this.mNumColumns = numColumns;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    /**
     * 设置响应拖拽的毫秒数，默认是1000毫秒
     *
     * @param dragResponseMS
     */
    public void setDragResponseMS(long dragResponseMS) {
        this.dragResponseMS = dragResponseMS;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) ev.getX() + getDP(12); // 阴影背景所以偏移12dp
                mDownY = (int) ev.getY() + getDP(12);
                int tempPosit = pointToPosition(mDownX, mDownY);

                if (tempPosit == AdapterView.INVALID_POSITION) {
                    return true;
                }
                if (mCustomGroup.isEditModel() && tempPosit != mDragPosition) {
                    mCustomGroup.setEditModel(false, 0);
                    return true;
                }
                mHandler.postDelayed(mLongClickRunnable, dragResponseMS);
                mStartDragItemView = getChildAt(mDragPosition - getFirstVisiblePosition());
                mPoint2ItemTop = mDownY - mStartDragItemView.getTop();
                mPoint2ItemLeft = mDownX - mStartDragItemView.getLeft();
                mOffset2Top = (int) (ev.getRawY() - mDownY);
                mOffset2Left = (int) (ev.getRawX() - mDownX);

                mDownScrollBorder = getHeight() / 5;
                mUpScrollBorder = getHeight() * 4 / 5;

                mStartDragItemView.setDrawingCacheEnabled(true);
                mDragBitmap = Bitmap.createBitmap(mStartDragItemView.getDrawingCache());
                mStartDragItemView.destroyDrawingCache();

                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX() + getDP(12);
                int moveY = (int) ev.getY() + getDP(12);
                if (isFirstLongDrag && !hasFirstCalculate) {
                    mStartDragItemView = getChildAt(mDragPosition - getFirstVisiblePosition());

                    mPoint2ItemTop = moveY - mStartDragItemView.getTop();
                    mPoint2ItemLeft = moveX - mStartDragItemView.getLeft();

                    mOffset2Top = (int) (ev.getRawY() - moveY);
                    mOffset2Left = (int) (ev.getRawX() - moveX);
                    hasFirstCalculate = true;
                }

                if (!isTouchInItem(mStartDragItemView, moveX, moveY)) {
                    mHandler.removeCallbacks(mLongClickRunnable);
                }
                break;
            case MotionEvent.ACTION_UP:
                mHandler.removeCallbacks(mLongClickRunnable);
                mHandler.removeCallbacks(mScrollRunnable);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isFirstLongDrag;
    private boolean hasFirstCalculate = false;

    public void drawWindowView(final int position, final MotionEvent event) {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                mDragPosition = position;
                if (mDragPosition != AdapterView.INVALID_POSITION) {
                    isFirstLongDrag = true;
                    mDragAdapter.setModifyPosition(mDragPosition);
                    mDownX = (int) event.getX();
                    mDownY = (int) event.getY();
                    mStartDragItemView = getChildAt(mDragPosition - getFirstVisiblePosition());
                    createFirstDragImage();
                }
            }
        }, 100);

    }

    private void createFirstDragImage() {
        removeDragImage();
        isDrag = true;
        mStartDragItemView.setDrawingCacheEnabled(true);
        mDragBitmap = Bitmap.createBitmap(mStartDragItemView.getDrawingCache());
        mStartDragItemView.destroyDrawingCache();

        mWindowLayoutParams = new WindowManager.LayoutParams();
        mWindowLayoutParams.format = 1;
        mWindowLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        int[] location = new int[2];
        mStartDragItemView.getLocationOnScreen(location);
        mWindowLayoutParams.x = location[0];
        mWindowLayoutParams.y = location[1] - mStatusHeight; // 12是阴影偏移
        mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.flags =
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        mDragImageView = new ImageView(getContext());
        mDragImageView.setImageBitmap(mDragBitmap);
        mDragImageView.setBackground(getResources().getDrawable(R.drawable.aihome_ufunc_bg));
        mWindowManager.addView(mDragImageView, mWindowLayoutParams);
        mStartDragItemView.setVisibility(View.INVISIBLE);// 隐藏该item
    }

    private boolean isTouchInItem(View dragView, int x, int y) {
        if (dragView == null) {
            return false;
        }
        int leftOffset = dragView.getLeft();
        int topOffset = dragView.getTop();
        if (x < leftOffset || x > leftOffset + dragView.getWidth()) {
            return false;
        }

        return y >= topOffset && y <= topOffset + dragView.getHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isDrag && mDragImageView != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    moveX = (int) ev.getX();
                    moveY = (int) ev.getY();

                    // 拖动item
                    onDragItem(moveX, moveY);
                    break;
                case MotionEvent.ACTION_UP:
                    int dropx = (int) ev.getX();
                    int dropy = (int) ev.getY();
                    onStopDrag(dropx, dropy);
                    isDrag = false;
                    isFirstLongDrag = false;
                    hasFirstCalculate = false;
                    cancleEditModel();
                    break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    public void cancleEditModel() {
        removeDragImage();
        mCustomGroup.setEditModel(false, 0);
    }

    private void createDragImage(Bitmap bitmap, int downX, int downY) {
        mWindowLayoutParams = new WindowManager.LayoutParams();
        mWindowLayoutParams.format = 1;
        mWindowLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        mWindowLayoutParams.x = downX - mPoint2ItemLeft + mOffset2Left;
        mWindowLayoutParams.y = downY - mPoint2ItemTop + mOffset2Top - mStatusHeight;
        mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.flags =
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        mDragImageView = new ImageView(getContext());
        mDragImageView.setImageBitmap(bitmap);
        mWindowManager.addView(mDragImageView, mWindowLayoutParams);
    }

    private void removeDragImage() {
        if (mDragImageView != null) {
            mWindowManager.removeView(mDragImageView);
            mDragImageView = null;
        }
    }

    private void onDragItem(int moveX, int moveY) {
        mWindowLayoutParams.x = moveX - mPoint2ItemLeft + mOffset2Left;
        mWindowLayoutParams.y = moveY - mPoint2ItemTop + mOffset2Top - mStatusHeight;
        mWindowManager.updateViewLayout(mDragImageView, mWindowLayoutParams); // 更新镜像的位置
        onSwapItem(moveX, moveY);

        // GridView自动滚动
        mHandler.post(mScrollRunnable);
    }

    @SuppressLint("NewApi")
    private Runnable mScrollRunnable = new Runnable() {

        @Override
        public void run() {
            int scrollY;
            if (getFirstVisiblePosition() == 0 || getLastVisiblePosition() == getCount() - 1) {
                mHandler.removeCallbacks(mScrollRunnable);
            }

            if (moveY > mUpScrollBorder) {
                scrollY = speed;
                mHandler.postDelayed(mScrollRunnable, 25);
            } else if (moveY < mDownScrollBorder) {
                scrollY = -speed;
                mHandler.postDelayed(mScrollRunnable, 25);
            } else {
                scrollY = 0;
                mHandler.removeCallbacks(mScrollRunnable);
            }
            smoothScrollBy(scrollY, 10);
        }
    };
    private CustomBehindParent parentView;

    /**
     * 交换item,并且控制item之间的显示与隐藏效果
     *
     * @param moveX
     * @param moveY
     */
    private void onSwapItem(int moveX, int moveY) {
        // 获取我们手指移动到的那个item的position
        final int tempPosition = pointToPosition(moveX, moveY);

        // 假如tempPosition 改变了并且tempPosition不等于-1,则进行交换
        if (tempPosition != mDragPosition && tempPosition != AdapterView.INVALID_POSITION && mAnimationEnd) {
            if (tempPosition != 0) {
                mDragAdapter.reorderItems(mDragPosition, tempPosition);
                mDragAdapter.setHideItem(tempPosition);

                final ViewTreeObserver observer = getViewTreeObserver();
                observer.addOnPreDrawListener(new OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        observer.removeOnPreDrawListener(this);
                        animateReorder(mDragPosition, tempPosition);
                        mDragPosition = tempPosition;
                        return true;
                    }
                });
            }
        }
    }

    private AnimatorSet createTranslationAnimations(View view, float startX, float endX, float startY, float endY) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "translationX", startX, endX);
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "translationY", startY, endY);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY);
        return animSetXY;
    }

    private void animateReorder(final int oldPosition, final int newPosition) {
        boolean isForward = newPosition > oldPosition;
        List<Animator> resultList = new LinkedList<Animator>();
        if (isForward) {
            for (int pos = oldPosition; pos < newPosition; pos++) {
                View view = getChildAt(pos - getFirstVisiblePosition());
                System.out.println(pos);

                if ((pos + 1) % mNumColumns == 0) {
                    resultList.add(createTranslationAnimations(view, -view.getWidth() * (mNumColumns - 1), 0,
                            view.getHeight(), 0));
                } else {
                    resultList.add(createTranslationAnimations(view, view.getWidth(), 0, 0, 0));
                }
            }
        } else {
            for (int pos = oldPosition; pos > newPosition; pos--) {
                View view = getChildAt(pos - getFirstVisiblePosition());
                if ((pos + mNumColumns) % mNumColumns == 0) {
                    resultList.add(createTranslationAnimations(view, view.getWidth() * (mNumColumns - 1), 0,
                            -view.getHeight(), 0));
                } else {
                    resultList.add(createTranslationAnimations(view, -view.getWidth(), 0, 0, 0));
                }
            }
        }

        AnimatorSet resultSet = new AnimatorSet();
        resultSet.playTogether(resultList);
        resultSet.setDuration(300);
        resultSet.setInterpolator(new AccelerateDecelerateInterpolator());
        resultSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mAnimationEnd = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimationEnd = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        resultSet.start();
    }

    private void onStopDrag(int dropx, int dropy) {

        View view = getChildAt(mDragPosition - getFirstVisiblePosition());

        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
        mDragAdapter.setHideItem(-1);
        removeDragImage();
    }

    private int getStatusHeight(Context context) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    public void refreshIconInfoList(ArrayList<DragIconInfo> iconInfoList) {
        mIconInfoList.clear();
        mIconInfoList.addAll(iconInfoList);
        mDragAdapter = new DragGridAdapter(mContext, mIconInfoList, this);
        this.setAdapter(mDragAdapter);
        mDragAdapter.notifyDataSetChanged();
    }

    public ArrayList<DragIconInfo> getEditList() {
        return mIconInfoList;
    }

    public void notifyDataSetChange(ArrayList<DragIconInfo> iconInfoList) {
        mIconInfoList.clear();
        mIconInfoList.addAll(iconInfoList);
        mDragAdapter.resetModifyPosition();
        mDragAdapter.notifyDataSetChanged();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        View localView1 = getChildAt(0);
        int column = getWidth() / localView1.getWidth();
        int childCount = getChildCount();
        Paint localPaint;
        localPaint = new Paint();
        localPaint.setStyle(Paint.Style.STROKE);
        localPaint.setColor(mContext.getResources().getColor(R.color.gap_line));
        for (int i = 0; i < childCount; i++) {
            View cellView = getChildAt(i);
            if ((i + 1) % column == 0) {
                canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(),
                        localPaint);
            } else if ((i + 1) > (childCount - (childCount % column))) {
                canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(),
                        localPaint);
            } else {
                canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(),
                        localPaint);
                canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(),
                        localPaint);
            }
        }
        if (childCount % column != 0) {
            for (int j = 0; j < (column - childCount % column); j++) {
                View lastView = getChildAt(childCount - 1);
                canvas.drawLine(lastView.getRight() + lastView.getWidth() * j, lastView.getTop(),
                        lastView.getRight() + lastView.getWidth() * j, lastView.getBottom(), localPaint);
            }
        }
    }

    private AnimatorSet createTranslationAnim(int position, int aimPosit, View view, ImageView animView) {
        int startx = view.getLeft();
        int starty = view.getTop();
        View aimView = getChildAt(aimPosit);
        int endx = aimView.getLeft();
        int endy = aimView.getTop();

        ObjectAnimator animX = ObjectAnimator.ofFloat(animView, "translationX", startx, endx);
        ObjectAnimator animY = ObjectAnimator.ofFloat(animView, "translationY", starty, endy);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(animView, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(animView, "scaleY", 1f, 0.5f);
        ObjectAnimator alpaAnim = ObjectAnimator.ofFloat(animView, "alpha", 1f, 0.0f);

        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY, scaleX, scaleY, alpaAnim);
        return animSetXY;
    }

    public void setDeletAnimView(CustomBehindParent customBehindParent) {
        this.parentView = customBehindParent;

    }

    public boolean isModifyedOrder() {
        return mDragAdapter.isHasModifyedOrder();
    }

    public void cancleModifyedOrderState() {
        mDragAdapter.setHasModifyedOrder(false);
    }

    public void resetHidePosition() {
        mDragAdapter.setHideItem(-1);
    }

    public boolean isValideEvent(MotionEvent ev, int scrolly) {
        int left = ((View) (getParent().getParent())).getLeft();
        int top = ((View) (getParent().getParent())).getTop();
        int x_ = (int) ev.getX();
        int y_ = (int) ev.getY();
        int tempx = x_ - left;
        int tempy = y_ - top + scrolly;
        int position = pointToPosition(tempx, tempy);
        Rect rect = new Rect();
        getHitRect(rect);
        return position != AdapterView.INVALID_POSITION;
    }

    public void clearDragView() {
        removeDragImage();
    }

    public void setHeightWidth(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = getDP(width);
            layoutParams.height = getDP(height);
            view.setLayoutParams(layoutParams);
        }

    }

    private int getDP(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public boolean isEditModel() {
        return mCustomGroup.isEditModel();
    }
}
