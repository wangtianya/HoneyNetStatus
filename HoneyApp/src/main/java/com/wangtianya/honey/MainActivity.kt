package com.wangtianya.honey

import android.databinding.DataBindingUtil
import android.databinding.DataBindingUtil.setContentView
import android.graphics.Color
import android.os.Bundle
import android.widget.Toolbar
import com.wangtianya.honey.databinding.ActivityMainBinding
import com.wangtianya.honey.util.initScreenColor
import com.wangtianya.yaa.core.activity.YaaActivity


class MainActivity : YaaActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initScreenColor(window, resources.getColor(R.color.colorPrimaryDark), Color.TRANSPARENT)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        initView()

    }

    fun initView() {
        setActionBar(binding.toolbar)
    }


}
