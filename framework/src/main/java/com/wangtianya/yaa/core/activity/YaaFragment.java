
package com.wangtianya.yaa.core.activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.wangtianya.yaa.core.context.YaaContext;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

/**
 * @name 自定义Fragment
 * @description 添加了ABaseHttp, YaaImageLoader。session保持需要在生产项目再次继承，个人实现
 * @contributor create, 14/11/2, Daya, i(a)wangtianya.cn
 */
public class YaaFragment extends Fragment {

    private RequestQueue mRequestQueue;

    public YaaFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue = Volley.newRequestQueue(YaaContext.getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRequestQueue.cancelAll(this);
    }

    public void addRequest(Request request) {
        request.setTag(this);
        mRequestQueue.add(request);
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }


}
