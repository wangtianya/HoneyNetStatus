package com.qjuzi.yaa.databinding.bindingadapter;


import com.qjuzi.yaa.databinding.BaseListViewAdapter;
import com.qjuzi.yaa.databinding.BaseRecycleViewAdapter;
import com.qjuzi.yaa.databinding.BaseRecycleViewHeaderFooterAdapter;
import com.qjuzi.yaa.ui.AlphaPressTouchListener;

import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by tianya on 2017/6/28.
 */
public class RecyclerViewAttrAdapter {


    @BindingAdapter({"android:adapter", "android:gridSpan"})
    public static void setAdapter(RecyclerView view, BaseRecycleViewAdapter adapter, int span) {
        view.setLayoutManager(new GridLayoutManager(view.getContext(), span));
        view.setAdapter(adapter);
    }

    @BindingAdapter({"android:adapter", "android:orientation"})
    public static void setAdapter1(RecyclerView view, BaseRecycleViewAdapter adapter, int orientation) {
        view.setLayoutManager(new LinearLayoutManager(view.getContext(), orientation, false));
        view.setAdapter(adapter);
    }


    @BindingAdapter({"android:adapter", "android:gridSpan"})
    public static void setAdapter(RecyclerView view, BaseRecycleViewHeaderFooterAdapter adapter, int span) {
        view.setLayoutManager(new GridLayoutManager(view.getContext(), span));
        view.setAdapter(adapter);
    }

    @BindingAdapter({"android:adapter", "android:orientation"})
    public static void setAdapter1(RecyclerView view, BaseRecycleViewHeaderFooterAdapter adapter, int orientation) {
        view.setLayoutManager(new LinearLayoutManager(view.getContext(), orientation, false));
        view.setAdapter(adapter);
    }
}
