package com.wangtianya.honey.pages.home

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import com.wangtianya.honey.R
import com.wangtianya.honey.databinding.ActivityMainBinding
import com.wangtianya.honey.util.ScreenManager
import com.wangtianya.yaa.core.activity.YaaActivity
import com.wangtianya.yaa.core.util.ScreenUtil
import com.wangtianya.yaa.core.util.YaaToast
import javax.xml.transform.OutputKeys.METHOD


class HomeActivity : YaaActivity() {

    lateinit var binding: ActivityMainBinding


    @SuppressWarnings(METHOD)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ScreenManager.initScreenColor(window, resources.getColor(R.color.colorPrimaryDark), Color.TRANSPARENT)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initSwipeRefreshLayout()
        initView()

    }

    fun initView() {
        setActionBar(binding.toolbar)
    }

    fun initSwipeRefreshLayout() {
        val dp200 = ScreenUtil.dip2px(200, this);
        binding.swipeRefreshView.setProgressViewOffset(true, 0, dp200)
        binding.swipeRefreshView.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        binding.swipeRefreshView.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        binding.swipeRefreshView.setOnRefreshListener {
            binding.swipeRefreshView.postDelayed({
                YaaToast.show("love")
            }, 1500)
        }
    }


}
