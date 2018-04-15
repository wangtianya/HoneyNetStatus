package com.wangtianya.honey.pages.home

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.Toast
import com.wangtianya.honey.R
import com.wangtianya.honey.databinding.ActivityMainBinding
import com.wangtianya.honey.tools.util.ScreenManager
import com.wangtianya.yaa.core.activity.YaaActivity
import com.wangtianya.yaa.core.context.YaaApplication
import com.wangtianya.yaa.core.context.YaaContext
import com.wangtianya.yaa.core.util.ScreenUtil
import com.wangtianya.yaa.core.util.YaaLog
import com.wangtianya.yaa.core.util.YaaToast
import com.wangtianya.yaa.net.ping2.PingTaskFactory
import com.wangtianya.yaa.net.ping2.inteface.PingListener
import com.wangtianya.yaa.net.ping2.inteface.PingResult
import com.wangtianya.yaa.net.ping2.inteface.PingRow


class HomeActivity : YaaActivity() {

    private val presenter = HomePresenter()

    lateinit var binding: ActivityMainBinding

    @SuppressWarnings
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 沉浸式，初始化StatusBar颜色
        ScreenManager.initScreenColor(window, resources.getColor(R.color.colorPrimaryDark), Color.TRANSPARENT)

        // 初始化数据
        presenter.initData()

        // 初始化UI
        initView()
    }

    private fun initView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.model = presenter.homeModel
        setActionBar(binding.toolbar)
        initSwipeRefreshLayout()
    }

    private fun initSwipeRefreshLayout() {
        val dp200 = ScreenUtil.dip2px(200, this);
        binding.swipeRefreshView.setProgressViewOffset(true, 0, dp200)
        binding.swipeRefreshView.setProgressBackgroundColor(android.R.color.white);
        binding.swipeRefreshView.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark)

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        binding.swipeRefreshView.setOnRefreshListener {
            testPing()
            binding.swipeRefreshView.postDelayed({
                binding.swipeRefreshView.isRefreshing = false
            }, 1000)
        }
    }


    fun testPing() {
        YaaToast.show((getConnectWifiSsid()))
    }

    fun getConnectWifiSsid(): String {
        val wifiManager = application.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        return wifiInfo.ssid
    }


}
