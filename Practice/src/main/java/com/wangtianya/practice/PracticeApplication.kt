package com.wangtianya.practice

import android.app.Application
import com.qjuzi.architecure.tree.context.Tree

class PracticeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Tree.init(this)
    }

}

