package cn.wangtianya.scaffold

import android.app.Application
import com.qjuzi.yaa.context.ContextCache

class ScaffoldApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // do something
        ContextCache.init(this)
    }

}

