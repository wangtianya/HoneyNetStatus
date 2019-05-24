package com.wangtianya.practice;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Intent;
import android.os.Bundle;

import com.qjuzi.architecure.base.context.ContextCache;
import com.wangtianya.learn.common.ItemActivity;
import com.wangtianya.learn.wiget.FragmentContainerActivity;
import com.wedcel.dragexpandgrid.DragGridTestFragment;

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


        startBaiduWork();
    }

    private void startBaiduWork() {
        Intent intent = new Intent(ContextCache.getContext(), BaiduWorkActivity.class);
        ContextCache.getContext().startActivity(intent);
    }

    private void startPractice() {
        Intent intent = new Intent(ContextCache.getContext(), BaiduWorkActivity.class);
        ContextCache.getContext().startActivity(intent);
    }
}
