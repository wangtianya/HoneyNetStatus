
package com.wangtianya.abase.core.activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.wangtianya.abase.core.context.ABaseContext;
import com.wangtianya.abase.image.cache.ImageLoader;
import com.wangtianya.abase.ioc.InjectUtil;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

/**
 * @name 自定义Fragment
 * @description 添加了ABaseHttp, ImageLoader。session保持需要在生产项目再次继承，个人实现
 * @contributor create, 14/11/2, Daya, i(a)wangtianya.cn
 */
public class ABaseFragment extends Fragment implements ABaseActivity.ActivityOnbackPressed {

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public ABaseFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageLoader = ImageLoader.newInstance();
        mRequestQueue = Volley.newRequestQueue(ABaseContext.getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InjectUtil.inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRequestQueue.cancelAll(this);
        mImageLoader.finish();
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
        return mImageLoader;
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
