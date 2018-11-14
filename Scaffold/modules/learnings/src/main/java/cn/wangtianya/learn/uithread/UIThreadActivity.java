/*
 *.
 */
package cn.wangtianya.learn.uithread;

import com.wangtianya.learn.common.ItemActivity;

import android.os.Bundle;

public class UIThreadActivity extends ItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addActivityItem("非UI线程进行绘制", NotUIDrawActivity.class);
    }
}
