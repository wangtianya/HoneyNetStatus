package com.qjuzi.qnet.pages.home.model

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Color
import android.view.View
import cn.wangtianya.yaa.binding.core.AbsStore
import com.qjuzi.lib.binding.extend.recycler.RecyclerBindingItemModel
import com.qjuzi.qnet.R
import com.qjuzi.qnet.databinding.ActivityMainBinding
import com.qjuzi.qnet.pages.home.HomeActivity
import com.qjuzi.qnet.pages.home.presenter.DelayTaskPresenter
import com.qjuzi.qnet.pages.home.presenter.HomeMainPresenter
import com.qjuzi.yaa.BR


/**
 * 1、承载页面所有的数据
 * 2、承载页面所有的事件分发
 */
class HomeStore(val context: HomeActivity) : AbsStore<HomeActivity>(context) {


    lateinit var binding: ActivityMainBinding

    lateinit var mainPresenter: HomeMainPresenter
    lateinit var delayTaskPresenter: DelayTaskPresenter

    /**
     * top banner data
     */
    val ip = ObservableField<String>("-")
    val netTypeIcon = ObservableInt(R.drawable.ic_signal_wifi_off)
    val statusColor = ObservableInt(Color.GRAY)
    val delay = ObservableField<String>("-")
    val up = ObservableField<String>("-")
    val down = ObservableField<String>("-")


    val gridList = ObservableArrayList<GridModel>()

    inner class GridModel(var imgId: Int, var imgTint: Int) : RecyclerBindingItemModel() {
        val title = ObservableField<String>("-")

        override fun getLayoutId(): Int {
            return R.layout.activity_main_grid_item
        }

        init {
            setVariable(BR.model, this)
        }

        fun onClick(view: View) {

        }
    }
}
