/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.qjuzi.lib.binding.extend.attr;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by tianya on 2017/6/28.
 */
public class ImageViewAttrAdapter {

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, Bitmap bitmap) {
        view.setImageBitmap(bitmap);
    }

    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }
}
