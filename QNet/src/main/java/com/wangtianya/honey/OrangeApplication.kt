package com.wangtianya.honey

import com.wangtianya.honey.common.broadcast.MyNetworkReceiver
import com.wangtianya.yaa.core.context.YaaApplication

/**
 * Created by wangtianya on 2018/4/14.
 */
class OrangeApplication : YaaApplication() {

    override fun onCreate() {
        super.onCreate()
        MyNetworkReceiver.register(this)
    }
}
