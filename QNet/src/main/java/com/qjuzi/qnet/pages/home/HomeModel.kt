package com.wangtianya.honey.pages.home

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Color
import com.wangtianya.honey.R
import android.databinding.ObservableArrayList
import android.view.View
import com.qjuzi.yaa.core.util.YaaToast
import com.qjuzi.yaa.databinding.AdapterBindingModel
import com.qjuzi.yaa.databinding.BaseRecycleViewAdapter


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



    val gridList = ObservableArrayList<GridModel>()
    val gridAdapter = ObservableField<BaseRecycleViewAdapter<GridModel>>()



    class GridModel(var imgId: Int, var title: String) : AdapterBindingModel() {
        fun onClick(view: View) {
            YaaToast.show("i love you")
        }
    }
}
