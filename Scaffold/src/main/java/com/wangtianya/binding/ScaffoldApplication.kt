package cn.wangtianya.scaffold

import android.app.Application
import com.qjuzi.architecure.base.context.ContextCache

class ScaffoldApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextCache.init(this)
    }

}

