package com.wangtianya.qnet

import android.app.Application
import com.meituan.android.walle.WalleChannelReader
import com.wangtianya.architecure.tree.Tree
import com.wangtianya.qnet.common.broadcast.MyNetworkReceiver
import com.qjuzi.tools.net.status.NetworkInfoManager
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy


/**
 * Created by wangtianya on 2018/4/14.
 */
class OrangeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Tree.init(this)
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

