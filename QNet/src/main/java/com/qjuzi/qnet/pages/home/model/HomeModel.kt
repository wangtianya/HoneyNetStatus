package com.qjuzi.qnet.pages.home.model

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Color
import android.view.View
import com.qjuzi.architecure.tree.TreeModel
import com.qjuzi.lib.binding.extend.common.BindingItemModel
import com.qjuzi.qnet.BR
import com.qjuzi.qnet.R
import com.qjuzi.qnet.pages.home.HomeActivity


/**
 * 1、承载页面所有的数据
 * 2、承载页面所有的事件分发
 */
class HomeModel : TreeModel<HomeActivity>() {

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

    inner class GridModel(var imgId: Int, var imgTint: Int) : BindingItemModel() {
        val title = ObservableField<String>("-")
        lateinit var clickListenr: View.OnClickListener

        override fun getLayoutId(): Int {
            return R.layout.activity_main_grid_item
        }

        init {
            setVariable(BR.model, this)
        }

        fun onClick(view: View) {
            if (clickListenr != null) {
                clickListenr.onClick(view)
            }
        }
    }
}
