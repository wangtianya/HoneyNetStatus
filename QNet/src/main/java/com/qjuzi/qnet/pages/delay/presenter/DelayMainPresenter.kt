package com.qjuzi.qnet.pages.delay.presenter

import android.graphics.Color
import com.qjuzi.qnet.R
import com.qjuzi.qnet.common.tools.util.ScreenManager
import com.qjuzi.qnet.pages.delay.model.DelayPageModel
import com.qjuzi.yaa.core.context.YaaContext
import com.qjuzi.yaa.databinding.BaseRecycleViewAdapter


class DelayMainPresenter(val model: DelayPageModel) {

    fun initData() {
        model.binding.model = model

        initStyle()

        initItemListAndAdapter()
    }


    private fun initStyle() {
        // 沉浸式，初始化StatusBar颜色
        val statusBarColor = @Suppress("deprecation") model.context.resources.getColor(R.color.colorPrimaryDark)
        val navBarColor = Color.TRANSPARENT
        ScreenManager.initScreenColor(model.context.activity.window, statusBarColor, navBarColor)
    }

    private fun initItemListAndAdapter() {

        for (i in 1..10) {
            var item = model.DelayItemModel("电信服务器" + i, "127.0.0.1", "https://static.qjuzi.com/web/imgicon-clue-xl.png")
            model.delayItemList.add(item)

        }

        val adapter = BaseRecycleViewAdapter<DelayPageModel.DelayItemModel>(YaaContext.getContext(), model.delayItemList)

        model.delayItemAdapter.set(adapter)
    }


}