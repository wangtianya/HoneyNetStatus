package com.qjuzi.qnet.pages.home.presenter

import android.graphics.Color
import cn.wangtianya.yaa.binding.core.AbsPresenter
import com.qjuzi.qnet.common.broadcast.MyNetworkReceiver
import com.qjuzi.qnet.common.tools.thread.ThreadUtil
import com.qjuzi.qnet.pages.home.model.HomeStore
import com.qjuzi.qnet.pages.home.tools.HomeHelper
import com.qjuzi.yaa.net.ping2.PingTaskFactory
import com.qjuzi.yaa.net.ping2.inteface.PingListener
import com.qjuzi.yaa.net.ping2.inteface.PingResult
import com.qjuzi.yaa.net.ping2.inteface.PingRow
import com.qjuzi.yaa.net.ping2.inteface.PingTask

class DelayTaskPresenter : AbsPresenter<HomeStore>() {

    private var pingTask: PingTask? = null

    fun startDelayDataUpdateTask() {
        ThreadUtil.runOnNotUI(Runnable { startDelayDataUpdate() })
    }

    fun stopDelayDataUpdateTask() {
        pingTask?.stop()
    }

    private fun startDelayDataUpdate() {
        stopDelayDataUpdateTask()
        pingTask = PingTaskFactory.newOne("www.baidu.com", object : PingListener {
            var i: Int = 0
            override fun onStart(row: PingRow) {
                store.statusColor.set(Color.GRAY)
                store.delay.set("-")
            }

            override fun onProgress(row: PingRow) {
                if (!MyNetworkReceiver.isAvailable || i++ % 2 == 0) return
                store.delay.set(HomeHelper.getDelayStr(row.time))
                store.statusColor.set(HomeHelper.getStatusColorByDelay(row.time))
            }

            override fun onFinish(result: PingResult) {
                store.delay.set("-")
            }
        })
        pingTask?.start()
    }
}