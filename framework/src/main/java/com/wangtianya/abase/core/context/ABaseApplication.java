
package com.wangtianya.abase.core.context;

import com.wangtianya.abase.core.activity.ActivityStack;

import android.app.Application;

/**
 * 自定义Application
 * i(a)wangtianya.cn
 */
public class ABaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ABaseContext.init(this);
    }



    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    public void exit() {
        ActivityStack.getActivityStack().exit();
    }
}
