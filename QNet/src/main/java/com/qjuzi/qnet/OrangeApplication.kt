package com.qjuzi.qnet

import android.content.SharedPreferences
import com.wangtianya.honey.common.broadcast.MyNetworkReceiver
import com.qjuzi.yaa.core.context.YaaApplication

/**
 * Created by wangtianya on 2018/4/14.
 */
class OrangeApplication : YaaApplication() {

    override fun onCreate() {
        super.onCreate()
        MyNetworkReceiver.register(this)
    }
}
