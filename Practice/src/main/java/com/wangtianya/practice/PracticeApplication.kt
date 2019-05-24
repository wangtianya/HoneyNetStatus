package com.wangtianya.practice

import android.app.Application
import com.qjuzi.architecure.base.context.ContextCache

class PracticeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextCache.init(this)
    }

}

