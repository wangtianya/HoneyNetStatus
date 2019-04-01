/*
 * Copyright (C) 2018 Godya. All Rights Reserved.
 */
package cn.wangtianya.learn.widget.ripple;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by wangtianya on 2018/3/6.
 */

public class HandleDispatchRelativeLayout extends RelativeLayout {
    public HandleDispatchRelativeLayout(Context context) {
        super(context);
    }

    public HandleDispatchRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HandleDispatchRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        String myId = getTag().toString();
        Log.e(myId + "dispatch", ev.getAction() + "");
        return super.dispatchTouchEvent(ev);
    }
}
