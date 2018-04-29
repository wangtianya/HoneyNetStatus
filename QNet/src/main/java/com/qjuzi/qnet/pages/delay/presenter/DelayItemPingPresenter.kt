package com.qjuzi.qnet.pages.delay.presenter

import android.graphics.Color
import com.qjuzi.qnet.common.broadcast.MyNetworkReceiver
import com.qjuzi.qnet.pages.delay.model.DelayPageModel
import com.qjuzi.qnet.pages.home.tools.HomeHelper
import com.qjuzi.yaa.net.ping2.PingTaskFactory
import com.qjuzi.yaa.net.ping2.inteface.PingListener
import com.qjuzi.yaa.net.ping2.inteface.PingResult
import com.qjuzi.yaa.net.ping2.inteface.PingRow

class DelayItemPingPresenter(val model: DelayPageModel) {

    fun startAllPing() {
        for (item in model.delayItemList) {
            startDelayItemPing(item)
        }
    }

    public fun stopAllPing() {
        for (item in model.delayItemList) {
            stopDelayItemPing(item)
        }
    }

    public fun startDelayItemPing(item: DelayPageModel.DelayItemModel) {
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