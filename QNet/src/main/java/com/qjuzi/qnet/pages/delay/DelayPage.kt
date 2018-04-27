package com.qjuzi.qnet.pages.delay

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qjuzi.qnet.R
import com.qjuzi.qnet.databinding.PageDelayBinding
import com.qjuzi.yaa.core.activity.YaaFragment

/**
 * Created by wangtianya on 2018/4/25.
 */


class DelayPage : YaaFragment() {

    var delayModel : DelayPageModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding : PageDelayBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.page_delay, null, false)
        delayModel = DelayPageModel(this, binding)

        delayModel!!.mainPresenter.initData()
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return delayModel!!.binding.root
    }

}