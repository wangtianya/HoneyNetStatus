package com.qjuzi.qnet.pages.home.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.graphics.Color
import android.text.TextUtils
import android.view.View
import com.qjuzi.architecure.tree.TreePresenter
import com.qjuzi.qnet.R
import com.qjuzi.qnet.common.broadcast.MyNetworkReceiver
import com.qjuzi.qnet.common.broadcast.NetworkChangedListener
import com.qjuzi.qnet.common.tools.thread.ThreadUtil
import com.qjuzi.qnet.manager.StyleManager
import com.qjuzi.qnet.pages.datacross.DataCrossPage
import com.qjuzi.qnet.pages.home.HomeActivity
import com.qjuzi.qnet.pages.home.tools.HomeHelper
import com.qjuzi.tools.core.activity.YaaFragmentContainerActivity
import com.qjuzi.tools.net.traffic.CurrentTrafficStats


class HomeMainPresenter : TreePresenter<HomeActivity>() {

    private val currentTrafficStats: CurrentTrafficStats = CurrentTrafficStats.getInstance()
    private lateinit var networkChangedListener: NetworkChangedListener

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initData() {
        page.binding.model = page.model
        StyleManager.getInstance().initStyle(page)
        initTopbar()

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

    private fun initTopbar() {
        page.setActionBar(page.binding.toolbar)
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

        val netInfoGrid = page.model.GridModel(R.drawable.ic_info, Color.RED)
        netInfoGrid.title.set(page.getString(R.string.home_info))
        page.model.gridList.add(netInfoGrid)

        val crossDataGrid = page.model.GridModel(R.drawable.ic_ping, Color.YELLOW)

        crossDataGrid.title.set(page.getString(R.string.home_ping))
        crossDataGrid.clickListenr = View.OnClickListener { YaaFragmentContainerActivity.startFragment(page, DataCrossPage::class.java) }
        page.model.gridList.add(crossDataGrid)

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