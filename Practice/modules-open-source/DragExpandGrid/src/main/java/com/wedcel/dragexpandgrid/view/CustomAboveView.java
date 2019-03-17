/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.wedcel.dragexpandgrid.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.wangtianya.practice.lib.draggrid.R;

import java.util.ArrayList;

public class CustomAboveView extends LinearLayout {

    private ArrayList<DragIconInfo> mIconInfoList = new ArrayList<>();
    private Context mContext;
    private CustomGroup mCustomGroup;
    private ItemViewClickListener mItemViewClickListener;
    private final int verticalViewWidth = 1;
    private ItemClickListener gridViewClickListener;
    private MotionEvent firstEvent;

    public interface ItemClickListener {
        void onSingleClicked(DragIconInfo iconInfo);

    }

    public CustomAboveView(Context context, CustomGroup customGoup) {
        super(context, null);
        this.mContext = context;
        this.mCustomGroup = customGoup;
        setOrientation(LinearLayout.VERTICAL);
    }

    public ItemClickListener getGridViewClickListener() {
        return gridViewClickListener;
    }

    public void setGridViewClickListener(ItemClickListener gridViewClickListener) {
        this.gridViewClickListener = gridViewClickListener;
    }

    public void refreshIconInfoList(ArrayList<DragIconInfo> iconInfoList) {
        mIconInfoList.clear();
        mIconInfoList.addAll(iconInfoList);
        refreshViewUI();
    }

    public ArrayList<DragIconInfo> getIconInfoList() {
        return mIconInfoList;
    }

    private int getDP(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void refreshViewUI() {
        removeAllViews();
        int rowNum =
                mIconInfoList.size() / CustomGroup.COLUMNUM + (mIconInfoList.size() % CustomGroup.COLUMNUM > 0 ? 1 : 0);
        LayoutParams rowParam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LayoutParams verticalParams = new LayoutParams(verticalViewWidth, LayoutParams.MATCH_PARENT);
        LayoutParams horizontalParams = new LayoutParams(LayoutParams.MATCH_PARENT, verticalViewWidth);
        for (int rowIndex = 0; rowIndex < rowNum; rowIndex++) {
            final View rowView = View.inflate(mContext, R.layout.gridview_above_rowview, null);

            LinearLayout llRowContainer = rowView.findViewById(R.id.gridview_rowcontainer_ll);
            final ImageView ivOpenFlag = rowView.findViewById(R.id.gridview_rowopenflag_iv);
            LinearLayout llBtm = rowView.findViewById(R.id.gridview_rowbtm_ll);

            LayoutParams itemParam = new LayoutParams(LayoutParams.WRAP_CONTENT, getDP(100));
            itemParam.weight = 1.0f;
            ItemViewClickListener itemClickLitener =
                    new ItemViewClickListener(llBtm, ivOpenFlag, new ItemViewClickInterface() {

                        @Override
                        public boolean shoudInteruptViewAnimtion(ItemViewClickListener listener, int position) {
                            boolean isInterupt = false;
                            mCustomGroup.clearEditDragView();
                            if (mItemViewClickListener != null && !mItemViewClickListener.equals(listener)) {
                                mItemViewClickListener.closeExpandView();
                            }
                            mItemViewClickListener = listener;
                            DragIconInfo iconInfo = mIconInfoList.get(position);
                            setViewCollaps();
                            isInterupt = true;
                            if (gridViewClickListener != null) {
                                gridViewClickListener.onSingleClicked(iconInfo);
                            }
                            return isInterupt;
                        }

                    });
            for (int columnIndex = 0; columnIndex < CustomGroup.COLUMNUM; columnIndex++) {
                View itemView = View.inflate(mContext, R.layout.gridview_behind_itemview, null);
                ImageView ivIcon = itemView.findViewById(R.id.icon_iv);
                TextView tvName = itemView.findViewById(R.id.name_tv);
                TextView noticeText = itemView.findViewById(R.id.noticeText);
                int itemInfoIndex = rowIndex * CustomGroup.COLUMNUM + columnIndex;
                if (itemInfoIndex > mIconInfoList.size() - 1) {
                    itemView.setVisibility(View.INVISIBLE);
                } else {
                    final DragIconInfo iconInfo = mIconInfoList.get(itemInfoIndex);
                    ivIcon.setImageResource(iconInfo.resIconId);
                    tvName.setText(iconInfo.name);
                    noticeText.setVisibility(iconInfo.index == 0 ? View.VISIBLE : View.GONE);
                    itemView.setId(itemInfoIndex);
                    itemView.setTag(itemInfoIndex);

                    itemView.setOnClickListener(itemClickLitener);
                    itemView.setOnLongClickListener(new OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View v) {
                            if (iconInfo.index != 0) {
                                int position = (Integer) v.getTag();
                                mCustomGroup.setEditModel(true, position);
                            }
                            return true;
                        }
                    });
                }

                llRowContainer.addView(itemView, itemParam);
                View view = new View(mContext);
                view.setBackgroundResource(R.color.gap_line);
                llRowContainer.addView(view, verticalParams);
            }
            View view = new View(mContext);
            view.setBackgroundResource(R.color.gap_line);
            addView(view, horizontalParams);
            addView(rowView, rowParam);
            if (rowIndex == rowNum - 1) {
                View btmView = new View(mContext);
                btmView.setBackgroundResource(R.color.gap_line);
                addView(btmView, horizontalParams);
            }

        }
    }

    public void setViewCollaps() {
        if (mItemViewClickListener != null) {
            mItemViewClickListener.closeExpandView();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.firstEvent = ev;
        if (mCustomGroup.isEditModel()) {
            mCustomGroup.sendEventBehind(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public MotionEvent getFirstEvent() {
        return firstEvent;
    }

    public void setFirstEvent(MotionEvent firstEvent) {
        this.firstEvent = firstEvent;
    }

    public interface ItemViewClickInterface {
        boolean shoudInteruptViewAnimtion(ItemViewClickListener animUtil, int position);
    }

    public class ItemViewClickListener implements OnClickListener {

        private View mContentParent;
        private ItemViewClickInterface animationListener;
        private final int INVALID_ID = -1000;
        private int mLastViewID = INVALID_ID;
        private View mViewFlag;

        private int startX;
        private int viewFlagWidth;
        private int itemViewWidth;

        public ItemViewClickListener(View contentParent, View viewFlag, ItemViewClickInterface animationListener) {
            this.mContentParent = contentParent;
            this.animationListener = animationListener;
            this.mViewFlag = viewFlag;
        }

        public View getContentView() {
            return mContentParent;
        }

        @Override
        public void onClick(View view) {
            int viewId = view.getId();
            boolean isTheSameView = false;
            if (animationListener != null) {
                if (animationListener.shoudInteruptViewAnimtion(this, viewId)) {
                    return;
                }
            }
            if (mLastViewID == viewId) {
                isTheSameView = true;
            } else {
                mViewFlag.setVisibility(View.VISIBLE);
                viewFlagWidth = getViewFlagWidth();
                itemViewWidth = view.getWidth();
                int endX = view.getLeft() + itemViewWidth / 2 - viewFlagWidth / 2;
                if (mLastViewID == INVALID_ID) {
                    startX = endX;
                    xAxismoveAnim(mViewFlag, startX, endX);
                } else {
                    xAxismoveAnim(mViewFlag, startX, endX);
                }
                startX = endX;
            }
            boolean isVisible = mContentParent.getVisibility() == View.VISIBLE;
            if (isVisible) {
                if (isTheSameView) {
                    animateCollapsing(mContentParent);
                }
            } else {
                if (isTheSameView) {
                    mViewFlag.setVisibility(View.VISIBLE);
                    xAxismoveAnim(mViewFlag, startX, startX);
                }
                animateExpanding(mContentParent);
            }
            mLastViewID = viewId;
        }

        private int getViewFlagWidth() {
            int viewWidth = mViewFlag.getWidth();
            if (viewWidth == 0) {
                int widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                int heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                mViewFlag.measure(widthSpec, heightSpec);
                viewWidth = mViewFlag.getMeasuredWidth();
            }
            return viewWidth;
        }

        public void xAxismoveAnim(View v, int startX, int toX) {
            moveAnim(v, startX, toX, 0, 0, 200);
        }

        private void moveAnim(View v, int startX, int toX, int startY, int toY, long during) {
            TranslateAnimation anim = new TranslateAnimation(startX, toX, startY, toY);
            anim.setDuration(during);
            anim.setFillAfter(true);
            v.startAnimation(anim);
        }

        public void closeExpandView() {
            boolean isVisible = mContentParent.getVisibility() == View.VISIBLE;
            if (isVisible) {
                animateCollapsing(mContentParent);
            }
        }

        public void animateCollapsing(final View view) {
            int origHeight = view.getHeight();

            ValueAnimator animator = createHeightAnimator(view, origHeight, 0);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                    mViewFlag.clearAnimation();
                    mViewFlag.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.start();
        }

        public void animateExpanding(final View view) {
            view.setVisibility(View.VISIBLE);
            final int widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            final int heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            view.measure(widthSpec, heightSpec);
            ValueAnimator animator = createHeightAnimator(view, 0, view.getMeasuredHeight());
            animator.start();
        }

        public ValueAnimator createHeightAnimator(final View view, int start, int end) {
            ValueAnimator animator = ValueAnimator.ofInt(start, end);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int value = (Integer) valueAnimator.getAnimatedValue();

                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = value;
                    view.setLayoutParams(layoutParams);
                }
            });
            return animator;
        }
    }
}
