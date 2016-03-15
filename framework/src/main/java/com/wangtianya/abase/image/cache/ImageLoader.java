
package com.wangtianya.abase.image.cache;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.Executor;

import com.wangtianya.abase.core.context.ABaseContext;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ImageLoader implements Observer {

    // TODO 以后做成单独的线城池给图片用，不和系统异步任务共用
    public static final Executor HREAD_POOL_EXECUTOR = null;

    private Set<LoadImageTask> mLoadImageTaskSet = new HashSet<LoadImageTask>();

    private ImageLoader() {
    }

    public static ImageLoader newInstance() {
        return new ImageLoader();
    }

    public void load(String url, ImageView imageview) {
        load(url, imageview, null, null);
    }

    public void load(String url, ImageView imageview, Bitmap defBitmap) {
        load(url, imageview, defBitmap, null);
    }

    public void load(String url, ImageView imageview, Bitmap defBitmap, Bitmap errorBitmap) {
        LoadImageTaskParam param = new LoadImageTaskParam(url, imageview, defBitmap, errorBitmap);
        param.addObserver(this);
        LoadImageTask task = new LoadImageTask(param);
        task.execute();
    }

    public void load(ImageView imageView, int drawableId) {
        Drawable drawable = ABaseContext.getContext().getResources().getDrawable(drawableId);
        imageView.setImageDrawable(drawable);
    }



    public boolean finish() {
        boolean isAllCancell = true;
        for (LoadImageTask task : mLoadImageTaskSet) {
            if (task.cancel(true)) {
                isAllCancell = false;
            }
        }
        return isAllCancell;
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data instanceof LoadImageTask) {
            mLoadImageTaskSet.remove(data);
        }
    }
}


