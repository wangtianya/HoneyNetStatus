
package com.qjuzi.yaa.context;

import android.app.Application;

public class YaaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        YaaContext.init(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
