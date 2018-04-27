package com.qjuzi.qnet.pages.delay

import android.graphics.Color
import com.qjuzi.qnet.R
import com.qjuzi.qnet.common.tools.util.ScreenManager
import com.qjuzi.yaa.core.context.YaaContext
import com.qjuzi.yaa.databinding.BaseRecycleViewAdapter

/**
 * Created by wangtianya on 2018/4/27.
 */

class DelayPresenter(val model: DelayPageModel) {

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
            var item = model.DelayItemModel("电信服务器" + i, null)
            model.delayItemList.add(item)
        }


        val adapter = BaseRecycleViewAdapter<DelayPageModel.DelayItemModel>(YaaContext.getContext(), model.delayItemList)

        model.delayItemAdapter.set(adapter)
    }

}