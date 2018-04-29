package com.qjuzi.qnet.common.binding;

import com.qjuzi.yaa.ui.AlphaPressTouchListener;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by wangtianya on 2018/4/14.
 */

public class CommonBindingAdapter {

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int drawable) {
        imageView.setImageResource(drawable);
    }

    @BindingAdapter("alphaPress")
    public static void alphaPress(LinearLayout view, boolean need) {
        if (need) {
            AlphaPressTouchListener.enable(view);
        }
    }
}
