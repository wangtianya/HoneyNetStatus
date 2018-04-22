package com.qjuzi.yaa.databinding.bindingadapter;


import com.qjuzi.yaa.ui.AlphaPressTouchListener;

import android.databinding.BindingAdapter;
import android.widget.LinearLayout;

/**
 * Created by tianya on 2017/6/28.
 */
public class CommonAttrAdapter {
    @BindingAdapter("android:background")
    public static void setSrc(LinearLayout view, int resId) {
        view.setBackgroundResource(resId);
    }

    @BindingAdapter("alphaPress")
    public static void alphaPress(LinearLayout view, boolean need) {
        if (need) {
            AlphaPressTouchListener.enable(view);
        }
    }
}
