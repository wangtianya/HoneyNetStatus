package com.qjuzi.qnet.pages.home.presenter

import android.graphics.Color
import android.text.TextUtils
import com.qjuzi.yaa.context.YaaContext
import com.qjuzi.yaa.databinding.BaseRecycleViewHeaderFooterAdapter
import com.qjuzi.qnet.R
import com.qjuzi.qnet.common.broadcast.MyNetworkReceiver
import com.qjuzi.qnet.common.broadcast.NetworkChangedListener
import com.qjuzi.qnet.common.tools.thread.ThreadUtil
import com.qjuzi.qnet.common.tools.util.ScreenManager
import com.qjuzi.qnet.pages.home.tools.HomeHelper
import com.qjuzi.qnet.pages.home.model.HomeModel
import com.qjuzi.yaa.core.util.ScreenUtil
import com.qjuzi.yaa.net.traffic.CurrentTrafficStats
import com.tencent.bugly.crashreport.CrashReport


class HomeMainPresenter(val homeModel: HomeModel) {

    // 需要被销毁的资源们
    private val currentTrafficStats: CurrentTrafficStats = CurrentTrafficStats.getInstance()
    private lateinit var networkChangedListener: NetworkChangedListener

    fun initData() {
        homeModel.binding.model = homeModel

        initStyle()
        initTopbar()
        initSwipeRefreshLayout()

        ThreadUtil.runOnNotUI(Runnable { initNetListener() })
        ThreadUtil.runOnNotUI(Runnable { initUpDownData() })
        ThreadUtil.runOnNotUI(Runnable { initGridListData() })
    }

    fun destory() {
        currentTrafficStats.stop()
        MyNetworkReceiver.removeListener(networkChangedListener)
        homeModel.delayTaskPresenter.stopDelayDataUpdateTask()
    }














    private fun initStyle() {
        // 沉浸式，初始化StatusBar颜色
        val statusBarColor = @Suppress("deprecation") homeModel.context.resources.getColor(R.color.colorPrimaryDark)
        val navBarColor = Color.TRANSPARENT
        ScreenManager.initScreenColor(homeModel.context.window, statusBarColor, navBarColor)
    }

    private fun initTopbar() {
        homeModel.context.setActionBar(homeModel.binding.toolbar)
    }

    private fun initSwipeRefreshLayout() {
        homeModel.binding.swipeRefreshView.setProgressViewOffset(true, 0, ScreenUtil.dip2px(200))
        @Suppress("deprecation")
        homeModel.binding.swipeRefreshView.setProgressBackgroundColor(android.R.color.white)
        homeModel.binding.swipeRefreshView
                .setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark)

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        homeModel.binding.swipeRefreshView.setOnRefreshListener {
            homeModel.binding.swipeRefreshView.postDelayed({
                homeModel.binding.swipeRefreshView.isRefreshing = false
                CrashReport.testJavaCrash()
            }, 1000)
        }
    }


    private fun initUpDownData() {
        currentTrafficStats.addCurrentTrafficListener { up, down ->
            if (MyNetworkReceiver.isAvailable) {
                homeModel.up.set(up)
                homeModel.down.set(down)
            } else {
                homeModel.up.set("-")
                homeModel.down.set("-")
            }
        };
        ThreadUtil.runOnNotUI(currentTrafficStats)
    }


    private fun initNetListener() {
        networkChangedListener = object : NetworkChangedListener {
            override fun call() {
                homeModel.statusColor.set(Color.GRAY)
                homeModel.delay.set("-")
                homeModel.netTypeIcon.set(HomeHelper.getNetTypeIcon())
                ThreadUtil.runOnNotUI(Runnable { updateIP() })
            }
        }
        MyNetworkReceiver.addListener(networkChangedListener)
    }


    private fun updateIP() {
        if (!MyNetworkReceiver.isAvailable) {
            homeModel.ip.set("网络已断开")
        } else {
            val wifiName = HomeHelper.getWifiName()
            val isp = HomeHelper.getIsp()

            val sBuilder = StringBuilder()

            if (!TextUtils.isEmpty(wifiName)) {
                sBuilder.append(wifiName)
                if (!TextUtils.isEmpty(isp)) {
                    sBuilder.append(" (").append(isp).append(")")
                }
            } else if (!TextUtils.isEmpty(isp)) {
                sBuilder.append(isp)
            }
            homeModel.ip.set(sBuilder.toString())
        }
    }

    private fun initGridListData() {
        val netInfoGrid = homeModel.GridModel(R.drawable.ic_signal_wifi_off, "详细信息")
        homeModel.gridList.add(netInfoGrid)
        val speedTestGrid = homeModel.GridModel(R.drawable.ic_signal_wifi_off, "网络测速")
        homeModel.gridList.add(speedTestGrid)
        val delayGrid = homeModel.GridModel(R.drawable.ic_signal_wifi_off, "网络延时")
        homeModel.gridList.add(delayGrid)

        val adapter = BaseRecycleViewHeaderFooterAdapter<HomeModel.GridModel>(YaaContext.getContext(),
                homeModel.gridList, homeModel.gridHeaderList, homeModel.gridFooterList)

        homeModel.gridAdapter.set(adapter)
    }


}