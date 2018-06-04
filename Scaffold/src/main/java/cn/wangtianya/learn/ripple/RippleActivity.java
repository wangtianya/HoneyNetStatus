/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.wangtianya.learn.ripple;

import com.wangtianya.learn.common.ItemActivity;

import android.os.Bundle;

/**
 * Created by tianya on 2017/7/28.
 */

public class RippleActivity extends ItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addItem("ListView 子控件里2", RippleInListItemActivity.class);
    }
}
