
package com.qjuzi.yaa.core.activity;

import com.qjuzi.yaa.context.YaaActivityStack;

import android.app.Activity;
import android.os.Bundle;

/**
 * @name 自定义Activity
 * @description 添加ActivityStack, ABaseHttp,
 * @contributor create, 14/11/2, Daya, i(a)wangtianya.cn
 */
public class YaaActivity extends Activity {

    private YaaActivityStack yaaActivityStack = YaaActivityStack.getActivityStack();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        yaaActivityStack.addActivity(this);
    }

    @Override
    public void finish() {
        yaaActivityStack.removeActivity(this);
        super.finish();
    }

}
