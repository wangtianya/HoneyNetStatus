package com.wangtianya.binding;

import android.os.Bundle;

import com.wangtianya.learn.common.ItemActivity;

import cn.wangtianya.learn.communication.CommunicationFragment;
import cn.wangtianya.learn.other.temp.utag.UniverseTagFragment;
import cn.wangtianya.learn.sysbase.SysBaseLearnFragment;
import cn.wangtianya.learn.other.openapi.OpenApiFragment;
import cn.wangtianya.learn.other.temp.TempTestFragment;
import cn.wangtianya.learn.ui.UILearnFragment;
import cn.wangtianya.learn.widget.WidgetLearnFragment;
import cn.wangtianya.learn.四大组件.FourBasicComponentMainFragment;

public class MainActivity extends ItemActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addFragmentItem("四大组件", FourBasicComponentMainFragment.class);
        addFragmentItem("UI基础", UILearnFragment.class);
        addFragmentItem("Widget", WidgetLearnFragment.class);

        addDiliver();
        addFragmentItem("系统基础", SysBaseLearnFragment.class);
        addFragmentItem("通信网络", CommunicationFragment.class);

        addDiliver();

        addFragmentItem("OpenApi", OpenApiFragment.class);
        addFragmentItem("Temp Test", TempTestFragment.class);

        addFragmentItem("Universe Tag", UniverseTagFragment.class);

    }
}
