package com.wangtianya.practice

import android.app.Application
import com.wangtianya.architecure.tree.Tree

class PracticeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Tree.init(this)
    }

}

