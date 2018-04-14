package com.wangtianya.honey.pages.home

import android.graphics.Color
import android.text.TextUtils
import com.wangtianya.honey.broadcast.MyNetworkReceiver
import com.wangtianya.honey.broadcast.NetworkChangedListener
import com.wangtianya.honey.tools.thread.ThreadUtil
import com.wangtianya.yaa.net.ping2.PingTaskFactory
import com.wangtianya.yaa.net.ping2.inteface.PingListener
import com.wangtianya.yaa.net.ping2.inteface.PingResult
import com.wangtianya.yaa.net.ping2.inteface.PingRow
import com.wangtianya.yaa.net.provider.ip.tool.GetIP
import com.wangtianya.yaa.net.provider.isp.ISPModel
import com.wangtianya.yaa.net.provider.isp.ISPProvider


class HomePresenter {

    val homeModel = HomeModel()

    fun initData() {
        ThreadUtil.runOnNotUI(Runnable { initNetListener() })
        ThreadUtil.runOnNotUI(Runnable { initDelayData() })

    }


    private fun initNetListener() {
        MyNetworkReceiver.addListener(object : NetworkChangedListener {
            override fun call() {
                homeModel.statusColor.set(Color.GRAY)
                homeModel.delay.set("-")
                homeModel.netTypeIcon.set(HomeHelper.getNetTypeIcon())
                ThreadUtil.runOnNotUI(Runnable { updateIP() })
            }
        })
    }


    private fun updateIP() {
        if (!MyNetworkReceiver.isAvailable) {
            homeModel.ip.set("网络已断开")
        } else {
            var ip = GetIP.getIpAddressFromCmyip()
            if (TextUtils.isEmpty(ip)) {
                ip = GetIP.getIntranetIp()
            }
            val ispModel: ISPModel? = ISPProvider.getInstance().getIspModel(ip)
            if (ispModel != null) {
                var ipBuilder = StringBuilder(ispModel.ip)
                if (!TextUtils.isEmpty(ispModel.isp)) {
                    ipBuilder.append(" (%s)".format(ispModel.isp))
                }
                homeModel.ip.set(ipBuilder.toString())
            } else {
                homeModel.ip.set(ip)
            }
        }
    }

    private fun initDelayData() {
        // todo: 根据网络状态、屏幕前台，停止和启动任务
        PingTaskFactory.newOne("www.baidu.com", object : PingListener {
            override fun onStart(row: PingRow) {
                homeModel.statusColor.set(Color.GRAY)
                homeModel.delay.set("-")
            }

            override fun onProgress(row: PingRow) {
                if (!MyNetworkReceiver.isAvailable) return
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
}