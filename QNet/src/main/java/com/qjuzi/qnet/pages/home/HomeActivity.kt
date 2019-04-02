package com.qjuzi.qnet.pages.home

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.qjuzi.architecure.mvvm.MVVMActivity
import com.qjuzi.architecure.mvvm.OnViewCreated
import com.qjuzi.qnet.databinding.ActivityMainBinding
import com.qjuzi.qnet.pages.home.model.HomeModel
import com.qjuzi.qnet.pages.home.presenter.DelayTaskPresenter
import com.qjuzi.qnet.pages.home.presenter.HomeMainPresenter
import com.qjuzi.qnet.pages.home.presenter.TestLifecyclePresenter

class HomeActivity : MVVMActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var model: HomeModel


    lateinit var mainPresenter: HomeMainPresenter
    lateinit var delayTaskPresenter: DelayTaskPresenter
    lateinit var testLifecyclePresenter: TestLifecyclePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    @OnViewCreated
    fun onViewCreated() {
        binding.recyclerView.layoutManager = GridLayoutManager(applicationContext, 4)
    }
}
