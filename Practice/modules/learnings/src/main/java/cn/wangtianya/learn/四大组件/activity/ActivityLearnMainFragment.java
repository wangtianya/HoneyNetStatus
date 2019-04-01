/*
 * Copyright (C) 2018 Godya. All Rights Reserved.
 */
package cn.wangtianya.learn.四大组件.activity;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wangtianya.learn.common.ItemFragment;

import cn.wangtianya.learn.四大组件.activity.task.SingleTaskLearnActivity;

public class ActivityLearnMainFragment extends ItemFragment {


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addActivityItem("SingleTaskLearnActivity", SingleTaskLearnActivity.class);

    }
}
