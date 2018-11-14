package com.qjuzi.qnet.pages.home.presenter

import android.graphics.Color
import android.text.TextUtils
import android.view.View
import cn.wangtianya.yaa.binding.core.AbsPresenter
import com.qjuzi.qnet.R
import com.qjuzi.qnet.common.broadcast.MyNetworkReceiver
import com.qjuzi.qnet.common.broadcast.NetworkChangedListener
import com.qjuzi.qnet.common.tools.thread.ThreadUtil
import com.qjuzi.qnet.common.tools.util.ScreenManager
import com.qjuzi.qnet.pages.home.model.HomeStore
import com.qjuzi.qnet.pages.home.tools.HomeHelper
import com.qjuzi.yaa.core.util.ScreenUtil
import com.qjuzi.yaa.core.util.YaaToast
import com.qjuzi.yaa.net.traffic.CurrentTrafficStats
import com.tencent.bugly.crashreport.CrashReport


class HomeMainPresenter : AbsPresenter<HomeStore>() {

    // 需要被销毁的资源们
    private val currentTrafficStats: CurrentTrafficStats = CurrentTrafficStats.getInstance()
    private lateinit var networkChangedListener: NetworkChangedListener

    fun initData() {

        store.binding.model = store

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
        store.delayTaskPresenter.stopDelayDataUpdateTask()
    }


    @Suppress("deprecation")
    private fun initStyle() {
        // 沉浸式，初始化StatusBar颜色
        val statusBarColor = store.page.resources.getColor(R.color.colorPrimaryDark)
        val navBarColor = Color.TRANSPARENT
        ScreenManager.initScreenColor(store.page.window, statusBarColor, navBarColor)
    }

    private fun initTopbar() {
        store.page.setActionBar(store.binding.toolbar)
    }

    @Suppress("deprecation")
    private fun initSwipeRefreshLayout() {
        store.binding.swipeRefreshView.setProgressViewOffset(true, 0, ScreenUtil.dip2px(200))
        store.binding.swipeRefreshView.setProgressBackgroundColor(android.R.color.white)
        store.binding.swipeRefreshView
                .setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark)

        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        store.binding.swipeRefreshView.setOnRefreshListener {
            store.binding.swipeRefreshView.postDelayed({
                store.binding.swipeRefreshView.isRefreshing = false
                CrashReport.testJavaCrash()
            }, 1000)
        }
    }


    private fun initUpDownData() {
        currentTrafficStats.addCurrentTrafficListener { up, down ->
            if (MyNetworkReceiver.isAvailable) {
                store.up.set(up)
                store.down.set(down)
            } else {
                store.up.set("-")
                store.down.set("-")
            }
        };
        ThreadUtil.runOnNotUI(currentTrafficStats)
    }


    private fun initNetListener() {
        networkChangedListener = object : NetworkChangedListener {
            override fun call() {
                store.statusColor.set(Color.GRAY)
                store.delay.set("-")
                store.netTypeIcon.set(HomeHelper.getNetTypeIcon())
                ThreadUtil.runOnNotUI(Runnable { updateIP() })
            }
        }
        MyNetworkReceiver.addListener(networkChangedListener)
    }


    private fun updateIP() {
        if (!MyNetworkReceiver.isAvailable) {
            store.ip.set("网络已断开")
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
            store.ip.set(sBuilder.toString())
        }
    }

    private fun initGridListData() {

        val netInfoGrid = store.GridModel(R.drawable.ic_info, Color.GRAY)
        netInfoGrid.title.set(store.page.getString(R.string.home_info))
        store.gridList.add(netInfoGrid)

        val speedTestGrid = store.GridModel(R.drawable.ic_download, Color.GRAY)
        speedTestGrid.title.set(store.page.getString(R.string.home_download))
        store.gridList.add(speedTestGrid)

        val pingGrid = store.GridModel(R.drawable.ic_ping, Color.GRAY)
        pingGrid.title.set(store.page.getString(R.string.home_ping))

        store.gridList.add(pingGrid)


        val regionGrid = store.GridModel(R.drawable.ic_internet, Color.GRAY)
        regionGrid.title.set(store.page.getString(R.string.home_delay_region))

        store.gridList.add(regionGrid)

        val dWebGrid = store.GridModel(R.drawable.ic_web, Color.GRAY)
        dWebGrid.title.set(store.page.getString(R.string.home_delay_web))
        store.gridList.add(dWebGrid)

        val dGameGrid = store.GridModel(R.drawable.ic_videogame, Color.GRAY)
        dGameGrid.title.set(store.page.getString(R.string.home_delay_game))
        store.gridList.add(dGameGrid)

        val moreGrid = store.GridModel(R.drawable.ic_directions_run, Color.GRAY)
        moreGrid.title.set(store.page.getString(R.string.home_comming))
        store.gridList.add(moreGrid)

    }


}