
package com.wangtianya.yaa.core.context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;

/**
 * 自定义Application
 * i(a)wangtianya.cn
 */
public class YaaApplication extends Application {

    private RequestQueue mRequestQueue = Volley.newRequestQueue(this);

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
        ActivityStack.getActivityStack().exit();
    }

    public void sendRequest(Request request) {
        request.setTag(this);
        mRequestQueue.add(request);
    }

    public void sendRequest(Request request, Object tag) {
        request.setTag(tag);
        mRequestQueue.add(request);
    }

    public void cancelRequest(Object tag) {
        mRequestQueue.cancelAll(tag);
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
