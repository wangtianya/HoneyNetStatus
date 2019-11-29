package com.qjuzi.qnet.pages.datacross

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qjuzi.architecure.tree.TreeFragment
import com.qjuzi.qnet.pages.delay.model.DelayPageModel

class DataCrossPage : TreeFragment() {

    lateinit var model : DelayPageModel
    lateinit var binding: DataCrossBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

}