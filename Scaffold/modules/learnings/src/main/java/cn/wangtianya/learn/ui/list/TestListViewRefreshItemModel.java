package cn.wangtianya.learn.ui.list;


import android.databinding.ObservableField;

import com.qjuzi.lib.binding.extend.list.ListBindingItemModel;

import cn.wangtianya.learn.R;

public class TestListViewRefreshItemModel extends ListBindingItemModel {

    public ObservableField<String> title = new ObservableField<>("");

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test_binding_list_view_refresh_item;
    }
}
