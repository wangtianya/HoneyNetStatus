package com.qjuzi.qnet.pages.home

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import com.wangtianya.honey.R
import com.wangtianya.honey.databinding.ActivityMainBinding
import com.wangtianya.honey.common.tools.util.ScreenManager
import com.qjuzi.yaa.core.activity.YaaActivity
import com.qjuzi.yaa.core.util.ScreenUtil.dip2px


class HomeActivity : YaaActivity() {

    private val presenter = HomePresenter()

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 沉浸式，初始化StatusBar颜色
        val statusBarColor = @Suppress("deprecation") resources.getColor(R.color.colorPrimaryDark)
        val navBarColor = Color.TRANSPARENT
        ScreenManager.initScreenColor(window, statusBarColor, navBarColor)

        // 初始化数据
        presenter.initData()

        // 初始化UI
        initView()
    }

    @SuppressLint("MissingSuperCall")
    override fun onDestroy() {
        presenter.destory()

        super.onDestroy()
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.model = presenter.homeModel
        setActionBar(binding.toolbar)
        initSwipeRefreshLayout()
    }

    private fun initSwipeRefreshLayout() {
        binding.swipeRefreshView.setProgressViewOffset(true, 0, dip2px(200))
        @Suppress("deprecation")
        binding.swipeRefreshView.setProgressBackgroundColor(android.R.color.white)
        binding.swipeRefreshView.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark)

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        binding.swipeRefreshView.setOnRefreshListener {
            binding.swipeRefreshView.postDelayed({
                binding.swipeRefreshView.isRefreshing = false
            }, 1000)
        }

    }


}
