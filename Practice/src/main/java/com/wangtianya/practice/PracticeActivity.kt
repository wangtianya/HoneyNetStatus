package com.wangtianya.practice

import android.os.Bundle
import cn.wangtianya.baidu.BaiduWorkFragment
import cn.wangtianya.learn.communication.CommunicationFragment
import cn.wangtianya.learn.sysbase.SysBaseLearnFragment
import cn.wangtianya.learn.ui.UILearnFragment
import cn.wangtianya.learn.widget.WidgetLearnFragment
import cn.wangtianya.learn.四大组件.FourBasicComponentMainFragment
import com.wangtianya.learn.common.ItemActivity

class PracticeActivity : ItemActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addFragmentItem("四大组件", FourBasicComponentMainFragment::class.java)
        addFragmentItem("UI基础", UILearnFragment::class.java)
        addFragmentItem("Widget", WidgetLearnFragment::class.java)

        addDiliver()
        addFragmentItem("系统基础", SysBaseLearnFragment::class.java)
        addFragmentItem("通信网络", CommunicationFragment::class.java)

        addDiliver()
        addFragmentItem("百度工作", BaiduWorkFragment::class.java)
    }
}
