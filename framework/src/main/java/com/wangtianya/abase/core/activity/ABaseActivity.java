
package com.wangtianya.abase.core.activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.wangtianya.abase.core.context.ABaseContext;
import com.wangtianya.abase.image.cache.ImageLoader;
import com.wangtianya.abase.ioc.InjectUtil;

import android.app.Activity;
import android.os.Bundle;

/**
 * @name 自定义Activity
 * @description 添加ActivityStack, ABaseHttp,
 * ImageLoader。session保持需要在生产项目再次继承，个人实现
 * @contributor create, 14/11/2, Daya, i(a)wangtianya.cn
 */
public class ABaseActivity extends Activity {

    private ActivityStack activityStack = ActivityStack.getActivityStack();
    private RequestQueue mRequestQueue;
    private ImageLoader imageLoader;

    private ABaseFragment mFragmentShouldAskOnBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStack.addActivity(this);
        mRequestQueue = Volley.newRequestQueue(ABaseContext.getContext());
    }

    @Override
    public void finish() {
        activityStack.removeActivity(this);
        mRequestQueue.cancelAll(this);
        super.finish();
    }

    public void addRequest(Request request) {
        request.setTag(this);
        getRequestQueue().add(request);
    }

    public void addRequest(Request request, Object tag) {
        request.setTag(tag);
        getRequestQueue().add(request);
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        if (imageLoader == null) {
            imageLoader = ImageLoader.newInstance();
        }
        return imageLoader;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        InjectUtil.inject(this);
    }

    /**
     * 默认反回true, 就是我这里ok了的意思, 你那里爱咋咋地
     */
    public interface ActivityOnbackPressed {
        boolean onBackPressed();
    }

    public void setFragmentShouldAskOnBackPressed(ABaseFragment fragment) {
        this.mFragmentShouldAskOnBackPressed = fragment;
    }
    @Override
    public void onBackPressed() {
        if (mFragmentShouldAskOnBackPressed != null && !mFragmentShouldAskOnBackPressed.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
}
