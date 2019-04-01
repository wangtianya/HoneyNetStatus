/*
 * Copyright (C) 2019 Godya. All Rights Reserved.
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

import android.widget.Toast;
import cn.wangtianya.practice.lib.draggrid.R;

import java.util.ArrayList;

public class CustomAboveView extends LinearLayout {

    private ArrayList<DragIconInfo> mIconInfoList = new ArrayList<>();
    private Context mContext;
    private CustomGroup mCustomGroup;
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
            LinearLayout llBtm = rowView.findViewById(R.id.gridview_rowbtm_ll);

            LayoutParams itemParam = new LayoutParams(LayoutParams.WRAP_CONTENT, getDP(100));
            itemParam.weight = 1.0f;
            ItemViewClickListener itemClickLitener =
                    new ItemViewClickListener(llBtm, new ItemViewClickInterface() {

                        @Override
                        public boolean shoudInteruptViewAnimtion(ItemViewClickListener listener, int position) {
                            boolean isInterupt = false;
                            mCustomGroup.clearEditDragView();
                            DragIconInfo iconInfo = mIconInfoList.get(position);
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
                    ivIcon.setBackground(mContext.getDrawable(iconInfo.resIconId));
                    tvName.setText(iconInfo.name);
                    if (iconInfo.index == 0) {
                        itemView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (event.getAction() == MotionEvent.ACTION_CANCEL
                                        || event.getAction() == MotionEvent.ACTION_UP) {
                                    noticeText.setVisibility(View.GONE);
                                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                    noticeText.setVisibility(View.VISIBLE);
                                }
                                return false;
                            }
                        });
                    } else {
                        itemView.setOnTouchListener(null);
                    }

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

        public ItemViewClickListener(View contentParent, ItemViewClickInterface animationListener) {
            this.mContentParent = contentParent;
            this.animationListener = animationListener;
        }

        public View getContentView() {
            return mContentParent;
        }

        @Override
        public void onClick(View view) {
            int viewId = view.getId();
            if (animationListener != null) {
                if (animationListener.shoudInteruptViewAnimtion(this, viewId)) {
                    return;
                }
            }

        }
    }
}
