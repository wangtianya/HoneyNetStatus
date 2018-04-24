package com.qjuzi.yaa.databinding.bindingadapter;


import com.qjuzi.yaa.databinding.BaseListViewAdapter;
import com.qjuzi.yaa.databinding.BaseRecycleViewAdapter;
import com.qjuzi.yaa.ui.AlphaPressTouchListener;

import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Created by tianya on 2017/6/28.
 */
public class ListAdapterAttrAdapter {
    @BindingAdapter("android:adapter")
    public static void setAdapter(ListView view, BaseAdapter adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter("android:adapter")
    public static void setAdapter(RecyclerView view, BaseRecycleViewAdapter adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter("android:gridSpan")
    public static void setGridLayoutManager(RecyclerView view, int span) {
        view.setLayoutManager(new GridLayoutManager(view.getContext(), span));
    }


}
