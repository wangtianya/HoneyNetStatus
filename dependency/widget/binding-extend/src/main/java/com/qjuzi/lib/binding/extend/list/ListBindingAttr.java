/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.qjuzi.lib.binding.extend.list;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.widget.ListView;

import com.qjuzi.lib.binding.extend.common.BindingItemModel;

/**
 * Created by tianya on 2018/9/18.
 */
public class ListBindingAttr {

    @SuppressWarnings("unchecked")
    @BindingAdapter({"android:items"})
    public static <T extends BindingItemModel> void setAdapter(
            ListView view, ObservableArrayList<T> models) {
        setAdapter(view, models, null, null);
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter({"android:items", "android:headers", "android:footers"})
    public static <T extends BindingItemModel> void setAdapter(
            ListView view,
            ObservableArrayList<T> models,
            ObservableArrayList<T> headers,
            ObservableArrayList<T> footers) {
        fixIndex(models);
        ListBindingAdapter<T> adapter = (ListBindingAdapter<T>) view.getAdapter();
        if (adapter == null) {
            adapter = new ListBindingAdapter(view.getContext(), models, headers, footers);
            view.setAdapter(adapter);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter({"android:items", "android:headers"})
    public static <T extends BindingItemModel> void setAdapterWithHeaders(
            ListView view,
            ObservableArrayList<T> models,
            ObservableArrayList<T> headers) {
        setAdapter(view, models, headers, null);
    }
    @SuppressWarnings("unchecked")
    @BindingAdapter({"android:items", "android:footers"})
    public static <T extends BindingItemModel> void setAdapterWithFooters(
            ListView view,
            ObservableArrayList<T> models,
            ObservableArrayList<T> footers) {
        setAdapter(view, models, null, footers);
    }

    public static <T extends BindingItemModel> void fixIndex(ObservableArrayList<T> models) {
        for (int i = 0; i < models.size(); i++) {
            models.get(i).index = i;
        }
    }
}
