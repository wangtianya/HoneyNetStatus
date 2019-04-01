/*
 * Copyright (C) 2018 Godya. All Rights Reserved.
 */
package cn.wangtianya.learn.sysbase;

import android.os.Bundle;

import com.wangtianya.learn.common.ItemFragment;

import cn.wangtianya.learn.sysbase.memory.MemoryFragment;

public class SysBaseLearnFragment extends ItemFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragmentItem("Memory相关", MemoryFragment.class);
    }
}
