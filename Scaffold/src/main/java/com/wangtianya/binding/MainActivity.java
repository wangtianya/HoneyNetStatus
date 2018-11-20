package com.wangtianya.binding;

import android.os.Bundle;

import com.wangtianya.learn.common.ItemActivity;

import cn.wangtianya.learn.ui.complex.ComplexUIMainFragment;
import cn.wangtianya.learn.四大组件.FourBasicComponentMainFragment;
import cn.wangtianya.learn.communication.CommunicationActivity;
import cn.wangtianya.learn.dialog.DialogActivity;
import cn.wangtianya.learn.event.EventActivity;
import cn.wangtianya.learn.html.HtmlFragment;
import cn.wangtianya.learn.memory.MemoryActivity;
import cn.wangtianya.learn.mvvm.MVVMActivity;
import cn.wangtianya.learn.openapi.OpenApiFragment;
import cn.wangtianya.learn.ripple.RippleActivity;
import cn.wangtianya.learn.temp.TempTestActivity;
import cn.wangtianya.learn.uithread.UIThreadActivity;

public class MainActivity extends ItemActivity {
    public static final String diliver = "------------------------------------------------------------------------------------";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addFragmentItem("四大组件", FourBasicComponentMainFragment.class);
        addActivityItem("通信", CommunicationActivity.class);

        addActivityItem(diliver, CommunicationActivity.class);

        addFragmentItem("复杂UI", ComplexUIMainFragment.class);

        addActivityItem(diliver, CommunicationActivity.class);

        addActivityItem("UI线程相关", UIThreadActivity.class);
        addActivityItem("MVVM相关", MVVMActivity.class);
        addActivityItem("Dialog相关", DialogActivity.class);
        addActivityItem("Memory相关", MemoryActivity.class);
        addActivityItem("RippleActivity相关", RippleActivity.class);
        addActivityItem("Event相关", EventActivity.class);
        addActivityItem(diliver, CommunicationActivity.class);
        addFragmentItem("HtmlParse相关", HtmlFragment.class);
        addActivityItem("Temp Test", TempTestActivity.class);
        addFragmentItem("OpenApi", OpenApiFragment.class);

    }
}
