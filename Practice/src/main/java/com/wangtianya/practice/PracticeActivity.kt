package com.wangtianya.practice

import android.os.Bundle
import cn.wangtianya.baidu.BaiduWorkFragment
import cn.wangtianya.learn.PraceticeFragment
import cn.wangtianya.learn.widget.bottomsheet.BottomSheetFragment
import com.wangtianya.learn.common.ItemActivity

class PracticeActivity : ItemActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragmentItem("我的练习", PraceticeFragment::class.java)
        addFragmentItem("百度工作", BaiduWorkFragment::class.java)

        addFragmentItem("BottomSheetFragment", BottomSheetFragment::class.java)


    }
}
