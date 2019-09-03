package com.qjuzi.tools.ui;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tianya on 2017/7/31.
 */

public class AlphaPressTouchListener implements View.OnTouchListener {

    private float alpha = 1f;

    public AlphaPressTouchListener(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int actionVal = event.getAction();
        if (actionVal == MotionEvent.ACTION_DOWN) {
            v.setAlpha(alpha);
        } else if (actionVal == MotionEvent.ACTION_UP || actionVal == MotionEvent.ACTION_CANCEL) {
            v.setAlpha(1f);
        }
        return false;
    }

    public static AlphaPressTouchListener texButton() {
        return new AlphaPressTouchListener(0.4f);
    }

    public static AlphaPressTouchListener textIconButton() {
        return new AlphaPressTouchListener(0.4f);
    }

    public static void enable(View view) {
        if (view == null) {
            return;
        }
        view.setOnTouchListener(new AlphaPressTouchListener(0.4f));
    }

    public static void enable(View view, float alpha) {
        if (view == null) {
            return;
        }
        view.setOnTouchListener(new AlphaPressTouchListener(alpha));
    }
}
