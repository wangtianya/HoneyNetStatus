package com.qjuzi.qnet

import com.qjuzi.qnet.common.broadcast.MyNetworkReceiver
import com.qjuzi.yaa.core.context.YaaApplication
import com.tencent.bugly.crashreport.CrashReport
import com.meituan.android.walle.WalleChannelReader
import com.tencent.bugly.crashreport.CrashReport.UserStrategy


/**
 * Created by wangtianya on 2018/4/14.
 */
class OrangeApplication : YaaApplication() {

    override fun onCreate() {
        super.onCreate()
        MyNetworkReceiver.register(this)
        initBugly()
    }


    fun initBugly() {
        val channel = WalleChannelReader.getChannel(this)

        val pkName = this.packageName
        val versionName = this.packageManager.getPackageInfo(pkName, 0).versionName
        val strategy = UserStrategy(this)

        strategy.appChannel = channel  //设置渠道
        strategy.appVersion = versionName      //App的版本
        strategy.appPackageName =  pkName //App的包名

        CrashReport.initCrashReport(this, "09bc61f923", true, strategy)
    }
}

