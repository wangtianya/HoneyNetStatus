/*
 * Copyright (C) 2018 Godya. All Rights Reserved.
 */
package cn.wangtianya.learn.sysbase;

import com.wangtianya.learn.common.ItemFragment;

import android.os.Bundle;
import android.view.View;
import cn.wangtianya.learn.sysbase.memory.MemoryFragment;

public class SysBaseLearnFragment extends ItemFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addFragmentItem("Memory相关", MemoryFragment.class);
    }
}
