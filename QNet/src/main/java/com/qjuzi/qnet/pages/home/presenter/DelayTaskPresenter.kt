package com.qjuzi.qnet.pages.home.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.graphics.Color
import com.qjuzi.architecure.tree.TreePresenter
import com.qjuzi.qnet.common.broadcast.MyNetworkReceiver
import com.qjuzi.qnet.common.tools.thread.ThreadUtil
import com.qjuzi.qnet.pages.home.HomeActivity
import com.qjuzi.qnet.pages.home.tools.HomeHelper
import com.qjuzi.yaa.net.ping2.PingTaskFactory
import com.qjuzi.yaa.net.ping2.inteface.PingListener
import com.qjuzi.yaa.net.ping2.inteface.PingResult
import com.qjuzi.yaa.net.ping2.inteface.PingRow
import com.qjuzi.yaa.net.ping2.inteface.PingTask

class DelayTaskPresenter : TreePresenter<HomeActivity>() {

    private var pingTask: PingTask? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startDelayDataUpdateTask() {
        ThreadUtil.runOnNotUI(Runnable { startDelayDataUpdate() })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopDelayDataUpdateTask() {
        pingTask?.stop()
    }

    private fun startDelayDataUpdate() {
        stopDelayDataUpdateTask()
        pingTask = PingTaskFactory.newOne("www.baidu.com", object : PingListener {
            var i: Int = 0
            override fun onStart(row: PingRow) {
                page.model.statusColor.set(Color.GRAY)
                page.model.delay.set("-")
            }

            override fun onProgress(row: PingRow) {
                if (!MyNetworkReceiver.isAvailable || i++ % 2 == 0) return
                page.model.delay.set(HomeHelper.getDelayStr(row.time))
                page.model.statusColor.set(HomeHelper.getStatusColorByDelay(row.time))
            }

            override fun onFinish(result: PingResult) {
                page.model.delay.set("-")
            }
        })
        pingTask?.start()
    }
}