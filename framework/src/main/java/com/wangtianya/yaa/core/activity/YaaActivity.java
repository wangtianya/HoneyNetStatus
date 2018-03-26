
package com.wangtianya.yaa.core.activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.wangtianya.yaa.core.context.YaaContext;
import com.wangtianya.yaa.core.context.ActivityStack;

import android.app.Activity;
import android.os.Bundle;

/**
 * @name 自定义Activity
 * @description 添加ActivityStack, ABaseHttp,
 * @contributor create, 14/11/2, Daya, i(a)wangtianya.cn
 */
public class YaaActivity extends Activity {

    private ActivityStack activityStack = ActivityStack.getActivityStack();
    private RequestQueue mRequestQueue = Volley.newRequestQueue(YaaContext.getContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStack.addActivity(this);
    }

    @Override
    public void finish() {
        activityStack.removeActivity(this);
        mRequestQueue.cancelAll(this);
        super.finish();
    }

    public void sendRequest(Request request) {
        request.setTag(this);
        mRequestQueue.add(request);
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }


}
