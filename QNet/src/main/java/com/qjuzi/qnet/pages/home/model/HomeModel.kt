package com.qjuzi.qnet.pages.home.model

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Color
import com.qjuzi.qnet.R
import android.databinding.ObservableArrayList
import android.view.View
import cn.wangtianya.yaa.binding.widget.BindingAdapterItemModel
import cn.wangtianya.yaa.binding.widget.BindingRecycleViewHeaderFooterAdapter
import com.qjuzi.qnet.databinding.ActivityMainBinding
import com.qjuzi.qnet.pages.delay.DelayPage
import com.qjuzi.qnet.pages.home.HomeActivity
import com.qjuzi.qnet.pages.home.presenter.DelayTaskPresenter
import com.qjuzi.qnet.pages.home.presenter.HomeMainPresenter
import com.qjuzi.yaa.BR
import com.qjuzi.yaa.core.activity.YaaFragmentContainerActivity


/**
 * 1、承载页面所有的数据
 * 2、承载页面所有的事件分发
 */
class HomeModel(val context: HomeActivity, val binding: ActivityMainBinding) {
    /**
     * 逻辑业务承载类，用于消耗Action
     */
    val mainPresenter = HomeMainPresenter(this) // 数据初始化，
    val delayTaskPresenter = DelayTaskPresenter(this) // 延时的持续更新

    /**
     * top banner data
     */
    val ip = ObservableField<String>("-")
    val netTypeIcon = ObservableInt(R.drawable.ic_signal_wifi_off)
    val statusColor = ObservableInt(Color.GRAY)
    val delay = ObservableField<String>("-")
    val up = ObservableField<String>("-")
    val down = ObservableField<String>("-")



    /**
     * grid app data
     */
    val gridHeaderList = ObservableArrayList<GridModel>()
    val gridList = ObservableArrayList<GridModel>()
    val gridFooterList = ObservableArrayList<GridModel>()
    val gridAdapter = ObservableField<BindingRecycleViewHeaderFooterAdapter<GridModel>>()

    inner class GridModel(var imgId: Int, var title: String) : BindingAdapterItemModel() {
        init {
            this.layoutId = R.layout.activity_main_grid_item
            this.variableId = BR.model
        }

        fun onClick(view: View) {
            YaaFragmentContainerActivity.startFragment(context,DelayPage().javaClass.name)
        }
    }
}
