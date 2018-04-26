package com.qjuzi.qnet.pages.home

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Color
import com.wangtianya.honey.R
import android.databinding.ObservableArrayList
import android.view.View
import com.qjuzi.yaa.BR
import com.qjuzi.yaa.core.util.YaaToast
import com.qjuzi.yaa.databinding.AdapterBindingModel
import com.qjuzi.yaa.databinding.BaseRecycleViewHeaderFooterAdapter


/**
 * Created by wangtianya on 2018/4/14.
 */
class HomeModel {

    val ip = ObservableField<String>("-")
    val netTypeIcon = ObservableInt(R.drawable.ic_signal_wifi_off)
    val statusColor = ObservableInt(Color.GRAY)
    val delay = ObservableField<String>("-")
    val up = ObservableField<String>("-")
    val down = ObservableField<String>("-")



    val gridHeaderList = ObservableArrayList<GridModel>()
    val gridList = ObservableArrayList<GridModel>()
    val gridFooterList = ObservableArrayList<GridModel>()
    val gridAdapter = ObservableField<BaseRecycleViewHeaderFooterAdapter<GridModel>>()



    class GridModel(var imgId: Int, var title: String) : AdapterBindingModel() {


        init {
            this.layoutId = R.layout.activity_main_grid_item
            this.variableId = BR.model

        }

        fun onClick(view: View) {
            YaaToast.show("i love you")
        }
    }
}
