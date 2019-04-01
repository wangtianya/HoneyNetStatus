/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.qjuzi.qnet.pages.home.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.qjuzi.architecure.mvvm.MVVMPresenter
import com.qjuzi.architecure.mvvm.OnViewCreated
import com.qjuzi.architecure.mvvm.OnViewDestroyed
import com.qjuzi.qnet.pages.home.HomeActivity
import com.qjuzi.yaa.core.util.YaaLog
import com.qjuzi.yaa.core.util.YaaToast

class TestLifecyclePresenter : MVVMPresenter<HomeActivity>() {


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