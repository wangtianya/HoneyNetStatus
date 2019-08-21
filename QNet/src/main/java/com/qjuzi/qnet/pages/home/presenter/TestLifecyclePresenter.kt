/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.qjuzi.qnet.pages.home.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.qjuzi.architecure.tree.TreePresenter
import com.qjuzi.architecure.tree.event.OnViewCreated
import com.qjuzi.architecure.tree.event.OnViewDestroyed
import com.qjuzi.qnet.pages.home.HomeActivity
import com.qjuzi.yaa.core.util.YaaLog
import com.qjuzi.yaa.core.util.YaaToast

class TestLifecyclePresenter : TreePresenter<HomeActivity>() {


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun ON_RESUME() {
        YaaToast.show("ON_RESUME")
    }

    @OnViewCreated
    private fun OnViewCreated() {
        YaaLog.getLogger().d("OnViewCreated")
    }

    @OnViewDestroyed
    private fun OnViewDestroyed() {
        YaaLog.getLogger().d("OnViewDestroyed")
    }
}