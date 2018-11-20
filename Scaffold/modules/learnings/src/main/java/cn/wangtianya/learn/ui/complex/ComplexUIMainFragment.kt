/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package cn.wangtianya.learn.ui.complex

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import kotlinx.android.synthetic.main.fragment_uic_complex_toolbar.*

import com.wangtianya.learn.common.ItemFragment

import cn.wangtianya.learn.R

class ComplexUIMainFragment : ItemFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_uic_complex_toolbar, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        activity.setActionBar(toolBar)
        toolBar.setLogo(R.drawable.ic_launcher)

        initStyle()
    }



    private fun initStyle() {
        // 沉浸式，初始化StatusBar颜色
        val statusBarColor : Int = 0xff303F9F.toInt()
        val navBarColor = Color.TRANSPARENT
        initScreenColor(activity.window, statusBarColor, navBarColor)
    }


    fun initScreenColor(window: Window, statusBarColor: Int, navigationBarColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

//                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = statusBarColor
            window.navigationBarColor = navigationBarColor
        }
    }
}
