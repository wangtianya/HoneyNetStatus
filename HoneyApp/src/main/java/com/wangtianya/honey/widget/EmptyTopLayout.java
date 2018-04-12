package com.wangtianya.honey.widget;

import com.wangtianya.yaa.core.context.YaaContext;
import com.wangtianya.yaa.core.util.ScreenUtil;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class EmptyTopLayout extends View {
    public EmptyTopLayout(Context context) {
        super(context);
    }

    public EmptyTopLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyTopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (Build.VERSION.SDK_INT >= 21) {
            int statusBarHeight = ScreenUtil.getStatusBarHeight(YaaContext.getContext());
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(statusBarHeight, MeasureSpec.EXACTLY));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
