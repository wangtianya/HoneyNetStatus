package com.wangtianya.honey.pages.home;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by wangtianya on 2018/4/14.
 */

public class HomeBindingAdapter {
    @BindingAdapter("android:svgColor")
    public static void setSvgColor(ImageView imageView, int color) {
        imageView.getDrawable().setTint(color);
    }

}
