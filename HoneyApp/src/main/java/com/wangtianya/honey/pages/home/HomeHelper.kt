package com.wangtianya.honey.pages.home

import android.graphics.Color
import com.wangtianya.honey.R
import com.wangtianya.honey.broadcast.MyNetworkReceiver

/**
 * Created by wangtianya on 2018/4/14.
 */


class HomeHelper {
    companion object {

        fun getNetTypeIcon(): Int {
            return if (!MyNetworkReceiver.isAvailable) {
                R.drawable.ic_signal_wifi_off
            } else {
                if (MyNetworkReceiver.isWifi()) R.drawable.ic_wifi else R.drawable.ic_phone_wifi
            }
        }

        fun getDelayStr(delayFloat: Float): String {
            return if (delayFloat > 0) {
                delayFloat.toString() + "毫秒"
            } else {
                "-"
            }
        }

        fun getStatusColorByDelay(delayFloat: Float): Int {
            return when (delayFloat) {
                in 0.01f..100f -> {
                    Color.GREEN
                }
                in 100..250 -> {
                    Color.YELLOW
                }
                in 200..Int.MAX_VALUE -> {
                    Color.RED
                }
                else -> {
                    Color.GRAY
                }
            }
        }
    }
}