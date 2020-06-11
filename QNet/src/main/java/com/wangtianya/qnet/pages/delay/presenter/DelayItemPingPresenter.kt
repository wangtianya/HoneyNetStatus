package com.wangtianya.qnet.pages.delay.presenter

import android.graphics.Color
import android.text.TextUtils
import com.wangtianya.qnet.common.broadcast.MyNetworkReceiver
import com.wangtianya.qnet.common.tools.thread.ThreadUtil
import com.wangtianya.qnet.pages.delay.model.DelayPageModel
import com.wangtianya.qnet.pages.home.tools.HomeHelper
import com.qjuzi.tools.net.ping2.PingTaskFactory
import com.qjuzi.tools.net.ping2.inteface.PingListener
import com.qjuzi.tools.net.ping2.inteface.PingResult
import com.qjuzi.tools.net.ping2.inteface.PingRow
import com.qjuzi.tools.net.provider.ip.tools.GetIP
import com.qjuzi.tools.net.provider.ip.tools.IPChecker

class DelayItemPingPresenter(val model: DelayPageModel) {

    fun startAllPing() {
        for (item in model.delayItemList) {
            startDelayItemPing(item)
            ThreadUtil.runOnNotUI(Runnable { updateHost(item) })
        }
    }

    fun stopAllPing() {
        for (item in model.delayItemList) {
            stopDelayItemPing(item)

        }
    }

    fun updateHost(item: DelayPageModel.DelayItemModel) {
        if (!IPChecker.isIPv4Address(item.host) && !TextUtils.isEmpty(GetIP.byDomain(item.host))) {
            val domain = item.host
            val ipTemp = GetIP.byDomain(item.host)
            item.ipText.set("$domain ($ipTemp)")
        }
    }

    fun startDelayItemPing(item: DelayPageModel.DelayItemModel) {
        stopDelayItemPing(item)

        item.pingTask = PingTaskFactory.newOne(item.host, object : PingListener {
            var i: Int = 0
            override fun onStart(row: PingRow) {
                item.statusColor.set(Color.GRAY)
                item.delayText.set("-")
            }

            override fun onProgress(row: PingRow) {
                if (!MyNetworkReceiver.isAvailable || i++ % 2 == 0) return
                item.delayText.set(HomeHelper.getDelayStr(row.time))
                item.statusColor.set(HomeHelper.getStatusColorByDelay(row.time))
            }

            override fun onFinish(result: PingResult) {
                item.delayText.set("-")
            }
        })
        item.pingTask?.start()
    }

    public fun stopDelayItemPing(item: DelayPageModel.DelayItemModel) {
        item.pingTask?.stop()
        item.pingTask = null
    }



}