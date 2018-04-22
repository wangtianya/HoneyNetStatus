
package com.qjuzi.yaa.other.deprecated.imagev1;

import java.util.Observable;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class LoadImageTaskParam extends Observable {
    private String url;
    private ImageView imageview;
    private Bitmap defBitmap;
    private Bitmap errorBitmap;

    public LoadImageTaskParam(String url, ImageView imageview, Bitmap defBitmap, Bitmap errorBitmap) {
        this.url = url;
        this.imageview = imageview;
        this.defBitmap = defBitmap;
        this.errorBitmap = errorBitmap;
    }

    public Bitmap getErrorBitmap() {
        return errorBitmap;
    }

    public void setErrorBitmap(Bitmap errorBitmap) {
        this.errorBitmap = errorBitmap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageView getImageview() {
        return imageview;
    }

    public void setImageview(ImageView imageview) {
        this.imageview = imageview;
    }

    public Bitmap getDefBitmap() {
        return defBitmap;
    }

    public void setDefBitmap(Bitmap defBitmap) {
        this.defBitmap = defBitmap;
    }
}
