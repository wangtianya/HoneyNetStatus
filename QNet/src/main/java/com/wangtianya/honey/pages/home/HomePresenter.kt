package com.wangtianya.honey.pages.home

import android.graphics.Color
import android.text.TextUtils
import com.wangtianya.honey.R
import com.wangtianya.honey.common.broadcast.MyNetworkReceiver
import com.wangtianya.honey.common.broadcast.NetworkChangedListener
import com.wangtianya.honey.common.tools.thread.ThreadUtil
import com.wangtianya.yaa.net.ping2.PingTaskFactory
import com.wangtianya.yaa.net.ping2.inteface.PingListener
import com.wangtianya.yaa.net.ping2.inteface.PingResult
import com.wangtianya.yaa.net.ping2.inteface.PingRow
import com.wangtianya.yaa.net.traffic.CurrentTrafficStats


// todo: 根据网络状态、屏幕前台，停止和启动任务
class HomePresenter {

    val homeModel = HomeModel()


    // 需要被销毁的资源们
    private val currentTrafficStats: CurrentTrafficStats = CurrentTrafficStats.getInstance()
    private lateinit var networkChangedListener: NetworkChangedListener

    fun initData() {
        ThreadUtil.runOnNotUI(Runnable { initNetListener() })
        ThreadUtil.runOnNotUI(Runnable { initDelayData() })
        ThreadUtil.runOnNotUI(Runnable { initUpDownData() })
        ThreadUtil.runOnNotUI(Runnable { initGridListData() })
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

    private fun initDelayData() {
        PingTaskFactory.newOne("www.baidu.com", object : PingListener {
            var i: Int = 0
            override fun onStart(row: PingRow) {
                homeModel.statusColor.set(Color.GRAY)
                homeModel.delay.set("-")
            }

            override fun onProgress(row: PingRow) {
                if (!MyNetworkReceiver.isAvailable || i++ % 2 == 0) return
                homeModel.delay.set(HomeHelper.getDelayStr(row.time))
                homeModel.statusColor.set(HomeHelper.getStatusColorByDelay(row.time))
            }

            override fun onFinish(result: PingResult) {
                homeModel.delay.set("-")
                try {
                    Thread.sleep(1500)
                } finally {
                    initDelayData()
                }
            }
        }).start()
    }


    private fun initGridListData() {
        val netInfoGrid = HomeModel.GridModel(R.drawable.ic_signal_wifi_off, "信息状态")
        homeModel.gridList.add(netInfoGrid)

        val speedTestGrid = HomeModel.GridModel(R.drawable.ic_signal_wifi_off, "网络测速")
        homeModel.gridList.add(speedTestGrid)

        val delayGrid = HomeModel.GridModel(R.drawable.ic_signal_wifi_off, "网络延时")
        homeModel.gridList.add(delayGrid)
    }


    fun destory() {
        currentTrafficStats.stop()
        MyNetworkReceiver.removeListener(networkChangedListener)
    }
}