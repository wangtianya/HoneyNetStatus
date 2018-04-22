package com.wangtianya.honey.pages.home

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Color
import com.wangtianya.honey.R
import android.databinding.ObservableArrayList
import com.wangtianya.yaa.databinding.AdapterBindingModel


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



    val gridList = ObservableArrayList<AdapterBindingModel>()



    class GridModel(var imgId: Int, var title: String) : AdapterBindingModel()
}
