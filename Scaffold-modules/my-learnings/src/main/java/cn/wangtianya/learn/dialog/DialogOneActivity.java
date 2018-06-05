/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.wangtianya.learn.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import cn.wangtianya.learn.R;

public class DialogOneActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_one);
    }

    @Override
    protected void onResume() {
        super.onResume();

        DialogOne.setConfig(Gravity.RIGHT, 1000);

    }
}
