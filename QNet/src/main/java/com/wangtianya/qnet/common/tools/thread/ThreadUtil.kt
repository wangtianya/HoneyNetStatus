package com.wangtianya.qnet.common.tools.thread

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ThreadUtil {

    companion object {
        private val mainLooperHandlder : Handler = Handler(Looper.getMainLooper())
        private val executor : Executor = Executors.newCachedThreadPool() as Executor

        fun runOnUI(runnable: () -> Unit) {
            mainLooperHandlder.post(runnable)
        }

        fun runOnUIDelay(runnable : Runnable, delay : Long) {
            mainLooperHandlder.postDelayed(runnable, delay)
        }

        fun runOnNotUI(runnable : Runnable) {
            executor.execute(runnable)
        }
    }
}