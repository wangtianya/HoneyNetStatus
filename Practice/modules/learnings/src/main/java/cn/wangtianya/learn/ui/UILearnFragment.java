/*
 * Copyright (C) 2018 Godya. All Rights Reserved.
 */package cn.wangtianya.learn.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wangtianya.learn.common.ItemFragment;

import cn.wangtianya.learn.ui.event.UIEventFragment;
import cn.wangtianya.learn.ui.html.HtmlFragment;
import cn.wangtianya.learn.ui.uithread.UIThreadFragment;

public class UILearnFragment extends ItemFragment {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addFragmentItem("UI线程相关", UIThreadFragment.class);
        addFragmentItem("HtmlParse相关", HtmlFragment.class);
        addFragmentItem("UI事件", UIEventFragment.class);
    }
}
