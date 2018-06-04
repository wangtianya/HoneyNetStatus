package com.qjuzi.yaa.databinding.bindingadapter;

import com.bumptech.glide.Glide;

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


    @BindingAdapter("android:src")
    public static void setSrc(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }

    @BindingAdapter("android:background")
    public static void setBackground(RelativeLayout view, int resId) {
        view.setBackgroundResource(resId);
    }
}
