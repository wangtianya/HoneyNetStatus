
package com.qjuzi.yaa.other.deprecated.imagev1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.qjuzi.yaa.context.ContextCache;
import com.qjuzi.yaa.persistence.IOHelper;
import com.qjuzi.yaa.persistence.MD5Util;
import com.qjuzi.yaa.persistence.SDCardHelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class LoadImageTask extends AsyncTask<Integer, Integer, Bitmap> {

    private LruCacheBitmap mLruCacheBitmap;
    private LoadImageTaskParam mParam;

    private InputStream mNetInputStream;

    public LoadImageTask(LoadImageTaskParam param) {
        this.mParam = param;
        mLruCacheBitmap = LruCacheBitmap.getSingleton();
    }

    @Override
    protected void onPreExecute() {
        if (mParam.getDefBitmap() != null) {
            mParam.getImageview().setImageBitmap(mParam.getDefBitmap());
        }
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        String urlStr = mParam.getUrl();
        String fileNameByUrl = MD5Util.MD5(urlStr);

        Bitmap cacheBitmap = mLruCacheBitmap.get(fileNameByUrl);
        if (cacheBitmap != null) {
            return cacheBitmap;
        }

        // 在磁盘缓存上找，找了后丢到内存去
        File fileCache = new File(SDCardHelper.getCacheDir() + File.separator + fileNameByUrl + ".png");
        if (fileCache.exists()) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                InputStream is = new FileInputStream(fileCache);
                Bitmap bitmap = BitmapFactory.decodeStream(is);// TODO 从本地读出来的图片其实可以压缩一下
                mLruCacheBitmap.put(fileNameByUrl, bitmap);
                is.close();
                return bitmap;
            } catch (FileNotFoundException e) {
                Log.e("1", e.getLocalizedMessage());
            } catch (IOException e) {
                Log.e("1", e.getLocalizedMessage());
            }
        }

        try {
            //如何获得InputSteam
            URL url = new URL(urlStr);
            if (urlStr.startsWith("http://")) {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                mNetInputStream = new BufferedInputStream(urlConnection.getInputStream());
            } else if (urlStr.startsWith("assets://")) {
                String fileName = urlStr.substring("assets://".length(), urlStr.length());
                mNetInputStream = ContextCache.getContext().getAssets().open(fileName);
            } else {
                mNetInputStream = new FileInputStream(new File(urlStr));
            }
            Bitmap bitmap = BitmapFactory.decodeStream(mNetInputStream);
            mLruCacheBitmap.put(fileNameByUrl, bitmap);
            InputStream is = ImageUtil.Bitmap2InputStream(bitmap);
            IOHelper.write(is, fileCache);
            mNetInputStream.close();
            is.close();
            return bitmap;
        } catch (Exception e) {
            Log.e("", e.getLocalizedMessage());
        }
        return mLruCacheBitmap.get(fileNameByUrl);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            mParam.getImageview().setImageBitmap(bitmap);
        } else if (mParam.getErrorBitmap() != null) {
            mParam.getImageview().setImageBitmap(mParam.getErrorBitmap());
        }
        mParam.notifyObservers(this);
    }

    @Override
    protected void onCancelled() {
        try {
            mNetInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mParam.notifyObservers(this);
        mLruCacheBitmap = null;
        mParam = null;
    }
}
