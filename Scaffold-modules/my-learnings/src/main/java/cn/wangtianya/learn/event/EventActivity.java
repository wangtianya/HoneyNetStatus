/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package cn.wangtianya.learn.event;

import com.wangtianya.learn.common.ItemActivity;

import android.os.Bundle;

public class EventActivity extends ItemActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragmentItem("touch dispath", TouchDispathFragment.class);
    }
}
