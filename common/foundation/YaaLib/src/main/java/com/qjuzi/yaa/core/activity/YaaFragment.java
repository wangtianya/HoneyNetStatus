
package com.qjuzi.yaa.core.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;

/**
 * @name 自定义Fragment
 * @description 添加了ABaseHttp, YaaImageLoader。session保持需要在生产项目再次继承，个人实现
 * @contributor create, 14/11/2, Daya, i(a)wangtianya.cn
 */
public class YaaFragment extends Fragment {

    public YaaFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
