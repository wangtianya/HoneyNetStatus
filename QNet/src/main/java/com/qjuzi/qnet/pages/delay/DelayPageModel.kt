package com.qjuzi.qnet.pages.delay

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.view.View
import com.qjuzi.qnet.R
import com.qjuzi.qnet.databinding.PageDelayBinding
import com.qjuzi.yaa.BR
import com.qjuzi.yaa.core.activity.YaaFragment
import com.qjuzi.yaa.core.util.YaaToast
import com.qjuzi.yaa.databinding.AdapterBindingModel
import com.qjuzi.yaa.databinding.BaseRecycleViewAdapter

/**
 * Created by wangtianya on 2018/4/25.
 */


public class DelayPageModel(val context: YaaFragment, var binding: PageDelayBinding) {

    val mainPresenter = DelayPresenter(this)




    val delayItemList = ObservableArrayList<DelayItemModel>()
    val delayItemAdapter = ObservableField<BaseRecycleViewAdapter<DelayItemModel>>()


    inner class DelayItemModel(var title: String, var ip: String?) : AdapterBindingModel() {

        init {
            this.layoutId = R.layout.page_delay_item
            this.variableId = BR.model
        }

        fun onClick(view: View) {
            YaaToast.show("i love you")
        }
    }


    public fun goBack(view: View) {
        context.activity.finish()
        YaaToast.show("i love you")
    }
}