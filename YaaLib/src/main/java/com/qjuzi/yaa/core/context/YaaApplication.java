
package com.qjuzi.yaa.core.context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;

/**
 * 自定义Application
 * i(a)wangtianya.cn
 */
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

    public void exit() {
        YaaActivityStack.getActivityStack().exit();
    }


}
