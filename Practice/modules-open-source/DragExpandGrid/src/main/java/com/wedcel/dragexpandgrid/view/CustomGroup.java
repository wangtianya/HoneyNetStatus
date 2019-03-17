/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.wedcel.dragexpandgrid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cn.wangtianya.practice.lib.draggrid.R;
import com.wedcel.dragexpandgrid.model.DragIconInfo;

import java.util.ArrayList;

public class CustomGroup extends ViewGroup {

	private CustomAboveView mCustomAboveView;
	private CustomBehindParent mCustomBehindParent;
	private boolean isEditModel = false;
	public static final int COLUMNUM = 3;
	private Context mContext;
	private ArrayList<DragIconInfo> allInfoList = new ArrayList<>();
	/**显示的带more的list*/
	private ArrayList<DragIconInfo> homePageInfoList = new ArrayList<DragIconInfo>();
	/**不可展开的list*/
	private ArrayList<DragIconInfo> onlyInfoList = new ArrayList<DragIconInfo>();

	private InfoEditModelListener editModelListener;



	public interface InfoEditModelListener {
		void onModleChanged(boolean isEditModel);
	}

	public CustomGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		LayoutParams upParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mCustomAboveView = new CustomAboveView(context, this);
		addView(mCustomAboveView, upParams);
		LayoutParams downParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mCustomBehindParent = new CustomBehindParent(mContext, this);
		addView(mCustomBehindParent, downParams);
		initData();
	}


	public CustomGroup(Context context) {
		this(context, null);
	}

	public InfoEditModelListener getEditModelListener() {
		return editModelListener;
	}

	public void setEditModelListener(InfoEditModelListener editModelListener) {
		this.editModelListener = editModelListener;
	}

	private void initData() {

		setCustomViewClickListener(new CustomAboveView.CustomAboveViewClickListener() {

			@Override
			public void onSingleClicked(DragIconInfo iconInfo) {
				dispatchSingle(iconInfo);
			}
		});

		initIconInfo();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMeasure = 0;
		int heightMeasure = 0;
		if (isEditModel) {
			mCustomBehindParent.measure(widthMeasureSpec, heightMeasureSpec);
			widthMeasure = mCustomBehindParent.getMeasuredWidth();
			heightMeasure = mCustomBehindParent.getMeasuredHeight();
		} else {
			mCustomAboveView.measure(widthMeasureSpec, heightMeasureSpec);
			widthMeasure = mCustomAboveView.getMeasuredWidth();
			heightMeasure = mCustomAboveView.getMeasuredHeight();
		}
		setMeasuredDimension(widthMeasure, heightMeasure);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (isEditModel) {
			int behindHeight = mCustomBehindParent.getMeasuredHeight();
			mCustomBehindParent.layout(l, 0, r, behindHeight + t);
		} else {
			int aboveHeight = mCustomAboveView.getMeasuredHeight();
			mCustomAboveView.layout(l, 0, r, aboveHeight + t);
		}
	}

	private void initIconInfo() {


		allInfoList.clear();
		allInfoList.addAll(initAllOriginalInfo());
		getPageInfoList();

		refreshIconInfo();
	}

	private ArrayList<DragIconInfo> initAllOriginalInfo() {
		ArrayList<DragIconInfo> iconInfoList = new ArrayList<DragIconInfo>();
		iconInfoList.add(new DragIconInfo(1, "第一个单独", R.drawable.ic_launcher, DragIconInfo.CATEGORY_ONLY));
		iconInfoList.add(new DragIconInfo(3, "第三个单独", R.drawable.ic_launcher, DragIconInfo.CATEGORY_ONLY ));
        iconInfoList.add(new DragIconInfo(2, "第二个单独", R.drawable.ic_launcher, DragIconInfo.CATEGORY_ONLY ));
        iconInfoList.add(new DragIconInfo(4, "第一个可展开", R.drawable.ic_launcher, DragIconInfo.CATEGORY_ONLY ));
		iconInfoList.add(new DragIconInfo(5, "第二个可展开", R.drawable.ic_launcher, DragIconInfo.CATEGORY_ONLY));
		iconInfoList.add(new DragIconInfo(6, "第三个可展开", R.drawable.ic_launcher, DragIconInfo.CATEGORY_ONLY ));

		return iconInfoList;
	}


	private void getPageInfoList() {
		homePageInfoList.clear();
		int count = 0;
		for (DragIconInfo info : allInfoList) {
			if (count < 9) {
				homePageInfoList.add(info);
				count++;
			} else {
				break;
			}
		}
	}

	private void refreshIconInfo() {
		judeHomeInfoValid();

		ArrayList<DragIconInfo> moreInfo = getMoreInfoList(allInfoList, homePageInfoList);
		onlyInfoList = getInfoByType(moreInfo, DragIconInfo.CATEGORY_ONLY);
		setIconInfoList(homePageInfoList);
	}

	private void judeHomeInfoValid() {
		boolean hasMoreInfo = false;
		int posit = 0;
		for(int index = 0;index<homePageInfoList.size();index++){
			DragIconInfo tempInfo = homePageInfoList.get(index);
			if(tempInfo.getId()==CustomAboveView.MORE){
				hasMoreInfo = true;
				posit = index;
				break;
			}
		}
		if(!hasMoreInfo){
			//没有更多 增加
			homePageInfoList.add(new DragIconInfo(CustomAboveView.MORE, "更多", R.drawable.ic_launcher, 0));
		}else{
			if(posit!=homePageInfoList.size()-1){
				//不是最后一个
				DragIconInfo moreInfo = homePageInfoList.remove(posit);
				homePageInfoList.add(moreInfo);
			}
		}
	}

	private ArrayList<DragIconInfo> getInfoByType(ArrayList<DragIconInfo> moreInfo, int categorySpt) {
		ArrayList<DragIconInfo> typeList = new ArrayList<>();
		for (DragIconInfo info : moreInfo) {
			if (info.getCategory() == categorySpt) {
				typeList.add(info);
			}
		}
		return typeList;
	}


	public void setCustomViewClickListener(CustomAboveView.CustomAboveViewClickListener gridViewClickListener) {
		mCustomAboveView.setGridViewClickListener(gridViewClickListener);
	}

	public void setIconInfoList(ArrayList<DragIconInfo> iconInfoList) {
		mCustomAboveView.refreshIconInfoList(iconInfoList);
		mCustomBehindParent.refreshIconInfoList(iconInfoList);
	}


	public boolean isEditModel() {
		return isEditModel;
	}

	public void cancleEidtModel(){
		setEditModel(false, 0);
	}


	public void setEditModel(boolean isEditModel, int position) {
		this.isEditModel = isEditModel;
		if (isEditModel) {
			mCustomAboveView.setViewCollaps();
			mCustomAboveView.setVisibility(View.GONE);
			mCustomBehindParent.notifyDataSetChange(mCustomAboveView.getIconInfoList());
			mCustomBehindParent.setVisibility(View.VISIBLE);
			mCustomBehindParent.drawWindowView(position, mCustomAboveView.getFirstEvent());
		} else {
			homePageInfoList.clear();
			homePageInfoList.addAll(mCustomBehindParent.getEditList());
			mCustomAboveView.refreshIconInfoList(homePageInfoList);
			mCustomAboveView.setVisibility(View.VISIBLE);
			mCustomBehindParent.setVisibility(View.GONE);
			if(mCustomBehindParent.isModifyedOrder()){
				mCustomBehindParent.cancleModifyOrderState();
			}
			mCustomBehindParent.resetHidePosition();
		}
		if(editModelListener!=null){
			editModelListener.onModleChanged(isEditModel);
		}
	}


	public void sendEventBehind(MotionEvent ev) {
		mCustomBehindParent.childDispatchTouchEvent(ev);
	}


	private ArrayList<DragIconInfo> getMoreInfoList(ArrayList<DragIconInfo> allInfoList, ArrayList<DragIconInfo> homePageInfoList) {
		ArrayList<DragIconInfo> moreInfoList = new ArrayList<DragIconInfo>();
		moreInfoList.addAll(allInfoList);
		moreInfoList.removeAll(homePageInfoList);
		return moreInfoList;
	}


	public void deletHomePageInfo(DragIconInfo dragIconInfo) {
		homePageInfoList.remove(dragIconInfo);
		mCustomAboveView.refreshIconInfoList(homePageInfoList);
		int category = dragIconInfo.getCategory();
		switch (category) {
			case DragIconInfo.CATEGORY_ONLY:
				onlyInfoList.add(dragIconInfo);
				break;
			default:
				break;
		}
		allInfoList.remove(dragIconInfo);
		allInfoList.add(dragIconInfo);
	}

	public void dispatchSingle(DragIconInfo dragInfo) {
		if (dragInfo == null) {
			return;
		}
		Toast.makeText(mContext, "点击了icon"+dragInfo.getName(), Toast.LENGTH_SHORT).show();


	}




	public boolean isValideEvent(MotionEvent ev, int scrolly) {
		return mCustomBehindParent.isValideEvent(ev,scrolly);
	}


	public void clearEditDragView() {
		mCustomBehindParent.clearDragView();
	}


}
