

/*
 *.
 */
package cn.wangtianya.learn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wangtianya.learn.common.ItemFragment;

import cn.wangtianya.learn.communication.CommunicationFragment;
import cn.wangtianya.learn.sysbase.SysBaseLearnFragment;
import cn.wangtianya.learn.ui.UILearnFragment;
import cn.wangtianya.learn.widget.WidgetLearnFragment;
import cn.wangtianya.learn.四大组件.FourBasicComponentMainFragment;

public class PraceticeFragment extends ItemFragment {


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        addFragmentItem("四大组件", FourBasicComponentMainFragment.class);
        addFragmentItem("UI基础", UILearnFragment.class);
        addFragmentItem("Widget", WidgetLearnFragment.class);

        addDiliver();
        addFragmentItem("系统基础", SysBaseLearnFragment.class);
        addFragmentItem("通信网络", CommunicationFragment.class);

        addDiliver();
    }
}
