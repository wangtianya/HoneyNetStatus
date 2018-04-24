package com.qjuzi.qnet.common.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by wangtianya on 2018/4/14.
 */

public class CommonBindingAdapter {

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int drawable) {
        imageView.setImageResource(drawable);
    }
}
