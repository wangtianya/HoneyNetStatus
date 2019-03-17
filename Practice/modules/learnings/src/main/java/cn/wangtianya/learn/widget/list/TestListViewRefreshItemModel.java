package cn.wangtianya.learn.widget.list;


import android.databinding.ObservableField;

import com.qjuzi.lib.binding.extend.common.BindingItemModel;

import cn.wangtianya.learn.R;

public class TestListViewRefreshItemModel extends BindingItemModel {

    public ObservableField<String> title = new ObservableField<>("");

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test_binding_list_view_refresh_item;
    }
}
