
package com.wangtianya.yaa.image.yaa;

import com.wangtianya.yaa.core.util.ABaseLog;

import android.graphics.Bitmap;
import android.util.LruCache;

public class LruCacheBitmap extends LruCache<String, Bitmap> {
    public static ABaseLog log = ABaseLog.getLogger();

    public static LruCacheBitmap sLruCacheBitmap;

    private LruCacheBitmap(int maxSize) {
        super(maxSize);
    }

    public static LruCacheBitmap getSingleton() {
        if (sLruCacheBitmap == null) {
            synchronized(LruCacheBitmap.class) {
                int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024 / 8);
                sLruCacheBitmap = new LruCacheBitmap(maxMemory);
                log.w("maxMemory:  " + maxMemory);
            }
        }
        return sLruCacheBitmap;
    }

    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        oldValue.recycle();
    }
}
