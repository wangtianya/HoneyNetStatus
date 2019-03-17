
/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.wedcel.dragexpandgrid.view;

import com.wedcel.dragexpandgrid.other.CommUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.wangtianya.practice.lib.draggrid.R;

public class CustomGridView extends LinearLayout {

	private Context mContext;

	public CustomGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
	private void initView(Context context) {
		this.mContext = context;
		int verticalViewWidth = CommUtil.dip2px(mContext, 1);
		View root = View.inflate(mContext, R.layout.gridview_child_layoutview, null);
		TextView textView = root.findViewById(R.id.gridview_child_name_tv);
		int widthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		int heightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		textView.measure(widthSpec, heightSpec);
		int viewHeight = textView.getMeasuredHeight();
		int viewWidth = (mContext.getResources().getDisplayMetrics().widthPixels - CommUtil.dip2px(mContext, 2))
				/ CustomGroup.COLUMNUM;
	}

	public CustomGridView(Context context) {
		super(context);
		initView(context);
	}
	public void setParentView(LinearLayout llBtm) {
		LinearLayout mParentView = llBtm;
	}
}
