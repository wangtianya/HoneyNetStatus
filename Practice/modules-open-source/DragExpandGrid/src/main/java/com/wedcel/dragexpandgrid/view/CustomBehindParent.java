package com.wedcel.dragexpandgrid.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import cn.wangtianya.practice.lib.draggrid.R;

import com.wedcel.dragexpandgrid.model.DragIconInfo;

import java.util.ArrayList;

public class CustomBehindParent extends RelativeLayout {

	private Context mContext;
	private CustomBehindView mCustomBehindEditView;


	public CustomBehindParent(Context context,CustomGroup customGroup) {
		super(context);
		this.mContext = context;
		mCustomBehindEditView = new CustomBehindView(context, customGroup);
		mCustomBehindEditView.setHorizontalSpacing(1);
		mCustomBehindEditView.setVerticalSpacing(1);
		mCustomBehindEditView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mCustomBehindEditView.setBackgroundColor(mContext.getResources().getColor(R.color.gap_line));
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(mCustomBehindEditView, lp);
		mCustomBehindEditView.setDeletAnimView(this);


	}

	public void refreshIconInfoList(ArrayList<DragIconInfo> iconInfoList) {
		mCustomBehindEditView.refreshIconInfoList(iconInfoList);
	}

	public void notifyDataSetChange(ArrayList<DragIconInfo> iconInfoList) {
		mCustomBehindEditView.notifyDataSetChange(iconInfoList);
	}

	public void drawWindowView(int position, MotionEvent event) {
		mCustomBehindEditView.drawWindowView(position,event);
	}


	public ArrayList<DragIconInfo> getEditList() {
		return mCustomBehindEditView.getEditList();
	}


	public void childDispatchTouchEvent(MotionEvent ev) {
		mCustomBehindEditView.dispatchTouchEvent(ev);
	}

	public boolean isModifyedOrder(){
		return mCustomBehindEditView.isModifyedOrder();
	}

	public void cancleModifyOrderState(){
		mCustomBehindEditView.cancleModifyedOrderState();
	}


	public void resetHidePosition() {
		mCustomBehindEditView.resetHidePosition();
	}

	public boolean isValideEvent(MotionEvent ev, int scrolly) {
		return mCustomBehindEditView.isValideEvent(ev,scrolly);
	}

	public void clearDragView() {
		mCustomBehindEditView.clearDragView();
	}

}
