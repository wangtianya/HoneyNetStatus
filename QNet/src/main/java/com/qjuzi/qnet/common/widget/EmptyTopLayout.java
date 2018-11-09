package com.qjuzi.qnet.common.widget;

import com.qjuzi.yaa.context.ContextCache;
import com.qjuzi.yaa.core.util.ScreenUtil;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * 沉浸式帮助类
 */
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
            int statusBarHeight = ScreenUtil.getStatusBarHeight(ContextCache.getContext());
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(statusBarHeight, MeasureSpec.EXACTLY));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
