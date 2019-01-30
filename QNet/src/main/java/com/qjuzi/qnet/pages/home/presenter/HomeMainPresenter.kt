package com.qjuzi.qnet.pages.home.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.graphics.Color
import android.text.TextUtils
import com.qjuzi.architecure.mvvm.MVVMPresenter
import com.qjuzi.qnet.R
import com.qjuzi.qnet.common.broadcast.MyNetworkReceiver
import com.qjuzi.qnet.common.broadcast.NetworkChangedListener
import com.qjuzi.qnet.common.tools.thread.ThreadUtil
import com.qjuzi.qnet.common.tools.util.ScreenManager
import com.qjuzi.qnet.pages.home.HomeActivity
import com.qjuzi.qnet.pages.home.tools.HomeHelper
import com.qjuzi.yaa.core.util.ScreenUtil
import com.qjuzi.yaa.net.traffic.CurrentTrafficStats
import com.tencent.bugly.crashreport.CrashReport


class HomeMainPresenter : MVVMPresenter<HomeActivity>() {

    // 需要被销毁的资源们
    private val currentTrafficStats: CurrentTrafficStats = CurrentTrafficStats.getInstance()
    private lateinit var networkChangedListener: NetworkChangedListener

    fun initData() {

        page.binding.model = page.model;

        initStyle()
        initTopbar()
        initSwipeRefreshLayout()

        ThreadUtil.runOnNotUI(Runnable { initNetListener() })
        ThreadUtil.runOnNotUI(Runnable { initUpDownData() })
        ThreadUtil.runOnNotUI(Runnable { initGridListData() })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destory() {
        currentTrafficStats.stop()
        MyNetworkReceiver.removeListener(networkChangedListener)
        page.delayTaskPresenter.stopDelayDataUpdateTask()
    }


    @Suppress("deprecation")
    private fun initStyle() {
        // 沉浸式，初始化StatusBar颜色
        val statusBarColor = page.resources.getColor(R.color.colorPrimaryDark)
        val navBarColor = Color.TRANSPARENT
        ScreenManager.initScreenColor(page.window, statusBarColor, navBarColor)
    }

    private fun initTopbar() {
        page.setActionBar(page.binding.toolbar)
    }

    @Suppress("deprecation")
    private fun initSwipeRefreshLayout() {
        page.binding.swipeRefreshView.setProgressViewOffset(true, 0, ScreenUtil.dip2px(200))
        page.binding.swipeRefreshView.setProgressBackgroundColor(android.R.color.white)
        page.binding.swipeRefreshView
                .setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark)

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        page.binding.swipeRefreshView.setOnRefreshListener {
            page.binding.swipeRefreshView.postDelayed({
                page.binding.swipeRefreshView.isRefreshing = false
                CrashReport.testJavaCrash()
            }, 1000)
        }
    }


    private fun initUpDownData() {
        currentTrafficStats.addCurrentTrafficListener { up, down ->
            if (MyNetworkReceiver.isAvailable) {
                page.model.up.set(up)
                page.model.down.set(down)
            } else {
                page.model.up.set("-")
                page.model.down.set("-")
            }
        };
        ThreadUtil.runOnNotUI(currentTrafficStats)
    }


    private fun initNetListener() {
        networkChangedListener = object : NetworkChangedListener {
            override fun call() {
                page.model.statusColor.set(Color.GRAY)
                page.model.delay.set("-")
                page.model.netTypeIcon.set(HomeHelper.getNetTypeIcon())
                ThreadUtil.runOnNotUI(Runnable { updateIP() })
            }
        }
        MyNetworkReceiver.addListener(networkChangedListener)
    }


    private fun updateIP() {
        if (!MyNetworkReceiver.isAvailable) {
            page.model.ip.set("网络已断开")
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
            page.model.ip.set(sBuilder.toString())
        }
    }

    private fun initGridListData() {

        val netInfoGrid = page.model.GridModel(R.drawable.ic_info, Color.GRAY)
        netInfoGrid.title.set(page.getString(R.string.home_info))
        page.model.gridList.add(netInfoGrid)

        val speedTestGrid = page.model.GridModel(R.drawable.ic_download, Color.GRAY)
        speedTestGrid.title.set(page.getString(R.string.home_download))
        page.model.gridList.add(speedTestGrid)

        val pingGrid = page.model.GridModel(R.drawable.ic_ping, Color.GRAY)
        pingGrid.title.set(page.getString(R.string.home_ping))

        page.model.gridList.add(pingGrid)


        val regionGrid = page.model.GridModel(R.drawable.ic_internet, Color.GRAY)
        regionGrid.title.set(page.getString(R.string.home_delay_region))

        page.model.gridList.add(regionGrid)

        val dWebGrid = page.model.GridModel(R.drawable.ic_web, Color.GRAY)
        dWebGrid.title.set(page.getString(R.string.home_delay_web))
        page.model.gridList.add(dWebGrid)

        val dGameGrid = page.model.GridModel(R.drawable.ic_videogame, Color.GRAY)
        dGameGrid.title.set(page.getString(R.string.home_delay_game))
        page.model.gridList.add(dGameGrid)

        val moreGrid = page.model.GridModel(R.drawable.ic_directions_run, Color.GRAY)
        moreGrid.title.set(page.getString(R.string.home_comming))
        page.model.gridList.add(moreGrid)

    }


}