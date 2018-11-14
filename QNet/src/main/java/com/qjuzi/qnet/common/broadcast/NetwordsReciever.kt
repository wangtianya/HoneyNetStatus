package com.qjuzi.qnet.common.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import com.qjuzi.architecure.base.context.ContextCache


/**
 * Created by wangtianya on 2018/4/14.
 */
class MyNetworkReceiver : BroadcastReceiver() {
    companion object {
        var isAvailable: Boolean = false
        var netType: Int = -1

        var listeners: ArrayList<NetworkChangedListener> = ArrayList()


        fun isWifi(): Boolean {
            return netType == ConnectivityManager.TYPE_WIFI
        }

        fun isMobile(): Boolean {
            return netType == ConnectivityManager.TYPE_MOBILE
        }

        // 7.0之后，如果用manifest注册，不在前台收不到
        fun register(context: Context) {
            val receiver = MyNetworkReceiver()
            val intentFilter = IntentFilter()
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
            context.registerReceiver(receiver, intentFilter)
        }

        fun update(info: NetworkInfo?) {
            if (info != null && info.isAvailable) {
                isAvailable = true
                netType = info.type
            } else {
                isAvailable = false
            }
            for (listener in MyNetworkReceiver.listeners)
                listener.call()
        }

        fun addListener(listener: NetworkChangedListener) {
            listeners.add(listener)

            val manager = ContextCache.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            update(manager.activeNetworkInfo)
        }

        fun removeListener(listener: NetworkChangedListener) {
            listeners.remove(listener)
        }

    }

    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        update(connectivityManager.activeNetworkInfo)
    }


}

interface NetworkChangedListener {
    fun call()
}