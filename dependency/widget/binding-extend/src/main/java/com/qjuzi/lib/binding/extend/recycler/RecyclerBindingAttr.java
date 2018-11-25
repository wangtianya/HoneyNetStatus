/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.qjuzi.lib.binding.extend.recycler;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qjuzi.lib.binding.extend.common.BindingItemModel;

/**
 * Created by tianya on 2018/9/18.
 */
public class RecyclerBindingAttr {


    @SuppressWarnings("unchecked")
    @BindingAdapter({"android:items"})
    public static <T extends BindingItemModel> void setAdapterWithHeaders(
            RecyclerView view,
            ObservableArrayList<T> models) {
        setAdapter(view, models, null, null, null);
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter({"android:items", "android:headers"})
    public static <T extends BindingItemModel> void setAdapterWithHeaders(
            RecyclerView view,
            ObservableArrayList<T> models,
            ObservableArrayList<T> headers) {
        setAdapter(view, models, headers, null, null);
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter({"android:items", "android:footers"})
    public static <T extends BindingItemModel> void setAdapterWithFooters(
            RecyclerView view,
            ObservableArrayList<T> models,
            ObservableArrayList<T> footers) {
        setAdapter(view, models, null, footers, null);
    }


    @SuppressWarnings("unchecked")
    @BindingAdapter({"android:items", "android:headers", "android:footers", "android:layoutManager"})
    public static <T extends BindingItemModel> void setAdapter(
            RecyclerView view,
            ObservableArrayList<T> models,
            ObservableArrayList<T> headers,
            ObservableArrayList<T> footers,
            RecyclerView.LayoutManager layoutManager) {

        if (view.getLayoutManager() == null) {
            if (layoutManager != null) {
                view.setLayoutManager(layoutManager);
            } else if (view.getLayoutManager() == null){
                view.setLayoutManager(new LinearLayoutManager(view.getContext()));
            }
        }

        fixIndex(models);
        RecyclerBindingAdapter<T> adapter = (RecyclerBindingAdapter<T>) view.getAdapter();
        if (adapter == null) {
            adapter = new RecyclerBindingAdapter(view.getContext(), models, headers, footers);
            view.setAdapter(adapter);
        }
    }

    public static <T extends BindingItemModel> void fixIndex(ObservableArrayList<T> models) {
        for (int i = 0; i < models.size(); i++) {
            models.get(i).index = i;
        }
    }
}
