package com.qjuzi.qnet

import com.qjuzi.qnet.common.broadcast.MyNetworkReceiver
import com.qjuzi.yaa.core.context.YaaApplication
import com.tencent.bugly.crashreport.CrashReport

/**
 * Created by wangtianya on 2018/4/14.
 */
class OrangeApplication : YaaApplication() {

    override fun onCreate() {
        super.onCreate()
        MyNetworkReceiver.register(this)
        CrashReport.initCrashReport(getApplicationContext(), "09bc61f923", true)
    }
}
