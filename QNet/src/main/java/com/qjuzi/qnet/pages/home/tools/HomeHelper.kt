package com.qjuzi.qnet.pages.home.tools

import android.graphics.Color
import android.text.TextUtils
import com.qjuzi.qnet.R
import com.qjuzi.qnet.common.broadcast.MyNetworkReceiver
import com.qjuzi.architecure.base.context.ContextCache
import com.qjuzi.yaa.net.provider.ip.tools.GetIP
import com.qjuzi.yaa.net.provider.isp.ISPModel
import com.qjuzi.yaa.net.provider.isp.ISPProvider
import com.qjuzi.yaa.net.tools.WifiUtil

/**
 * Helper的作用：有一些比较杂逻辑，写在presenter里会导致逻辑比较乱，影响业务的阅读。
 */
class HomeHelper {
    companion object {

        fun getWifiName(): String {
            var wifiName = ""
            if (MyNetworkReceiver.isWifi()) {
                wifiName = WifiUtil.getConnectWifiSsid(ContextCache.getContext())
                wifiName = wifiName.replace("\"", "")
            }
            return wifiName
        }

        fun getIp() : String {
            var ip = GetIP.getIpAddressFromWeb()
            if (TextUtils.isEmpty(ip)) {
                ip = GetIP.getIntranetIp()
            }
            return ip
        }

        fun getIsp() : String {
            val ip = getIp()
            var isp = ""
            val ispModel: ISPModel? = ISPProvider.getInstance().getIspModel(ip)

            if (ispModel != null && !TextUtils.isEmpty(ispModel.isp) && !TextUtils.equals("XX", ispModel.isp)) {
                isp = ispModel.isp
            }
            return isp
        }

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