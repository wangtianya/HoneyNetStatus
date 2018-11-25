package com.qjuzi.qnet.pages.delay.model

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.view.View
import com.qjuzi.lib.binding.extend.common.BindingItemModel
import com.qjuzi.qnet.R
import com.qjuzi.qnet.databinding.PageDelayBinding
import com.qjuzi.qnet.pages.delay.presenter.DelayItemPingPresenter
import com.qjuzi.qnet.pages.delay.presenter.DelayMainPresenter
import com.qjuzi.yaa.BR
import com.qjuzi.yaa.core.activity.YaaFragment
import com.qjuzi.yaa.core.util.YaaToast
import com.qjuzi.yaa.net.ping2.inteface.PingTask

/**
 * Created by wangtianya on 2018/4/25.
 */
@Suppress("unused")
class DelayPageModel(val context: YaaFragment, var binding: PageDelayBinding){

    val mainPresenter = DelayMainPresenter(this)
    val itemPingPresenter = DelayItemPingPresenter(this)

    val delayItemList = ObservableArrayList<DelayItemModel>()

    inner class DelayItemModel(var title: String, var host: String?, var resId: Int?) : BindingItemModel() {
        override fun getLayoutId(): Int {
            return R.layout.page_delay_item
        }

        val statusColor : ObservableField<Int> = ObservableField(0)
        val delayText : ObservableField<String> = ObservableField("-")
        val ipText : ObservableField<String?> = ObservableField(host)

        var pingTask : PingTask? = null

        init {
            setVariable(BR.model, this)
        }


        fun onClick(view: View) {
            YaaToast.show("i love you1")
        }
    }

    fun goBack(view: View) {
        context.activity.finish()
        YaaToast.show("i love you")
    }
}