package com.wangtianya.yaa.image.imageloader;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by wangtianya on 2018/3/20.
 */
public class ImageLoaderBindAdapter {

    @BindingAdapter({"imageUrl"})
    public static void displayImage(ImageView view, String url) {
        ImageLoader.getInstance().displayImage(url, view);
    }

}
