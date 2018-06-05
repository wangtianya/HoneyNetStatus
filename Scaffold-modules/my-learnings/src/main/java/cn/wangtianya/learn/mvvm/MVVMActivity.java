/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.wangtianya.learn.mvvm;

import com.wangtianya.learn.common.ItemActivity;

import android.os.Bundle;

public class MVVMActivity extends ItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addItem("DataBinding Basic1", DataBindingActivity.class);
        addItem("DataBinding Basic2", DataBinding2Activity.class);
        addItem("DataBinding Basic3", DataBinding3Activity.class);
        addItem("标准Model Test", DataBindingMubanActivity.class);
    }
}
