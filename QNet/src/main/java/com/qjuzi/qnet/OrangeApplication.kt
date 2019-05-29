package com.qjuzi.qnet

import android.app.Application
import com.meituan.android.walle.WalleChannelReader
import com.qjuzi.architecure.base.context.ContextCache
import com.qjuzi.qnet.common.broadcast.MyNetworkReceiver
import com.qjuzi.qnet.net.NetworkInfoManager
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy


/**
 * Created by wangtianya on 2018/4/14.
 */
class OrangeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextCache.init(this)
        NetworkInfoManager.getInstance().init(this)
        MyNetworkReceiver.register(this)
        initBugly()
    }


    fun initBugly() {
        val channel = WalleChannelReader.getChannel(this)

        val pkName = this.packageName
        val versionName = this.packageManager.getPackageInfo(pkName, 0).versionName
        val strategy = UserStrategy(this)
        strategy.appReportDelay = 15000
        strategy.appChannel = channel  //设置渠道
        strategy.appVersion = versionName      //App的版本
        strategy.appPackageName =  pkName //App的包名

        CrashReport.initCrashReport(this, "09bc61f923", true, strategy)
    }
}

