package com.qjuzi.qnet.pages.delay.presenter

import android.graphics.Color
import cn.wangtianya.yaa.binding.widget.BindingRecycleViewAdapter
import com.qjuzi.qnet.R
import com.qjuzi.qnet.common.tools.util.ScreenManager
import com.qjuzi.qnet.pages.delay.model.DelayPageModel
import com.qjuzi.yaa.context.ContextCache


class DelayMainPresenter(val model: DelayPageModel) {

    fun initData() {
        model.binding.model = model

        initStyle()

        initItemListAndAdapter()
    }


    @Suppress("deprecation")
    private fun initStyle() {
        // 沉浸式，初始化StatusBar颜色
        val statusBarColor = model.context.resources.getColor(R.color.colorPrimaryDark)
        val navBarColor = Color.TRANSPARENT
        ScreenManager.initScreenColor(model.context.activity.window, statusBarColor, navBarColor)
    }

    private fun initItemListAndAdapter() {

        for (i in 1..5) {
            var item = model.DelayItemModel("电信服务器", "www.baidu.com", R.drawable.ic_videogame)
            model.delayItemList.add(item)
        }

        var item = model.DelayItemModel("电信服务器", "127.0.0.1", R.drawable.ic_server)
        model.delayItemList.add(item)

        val adapter = BindingRecycleViewAdapter<DelayPageModel.DelayItemModel>(ContextCache.getContext(), model.delayItemList)

        model.delayItemAdapter.set(adapter)
    }


}