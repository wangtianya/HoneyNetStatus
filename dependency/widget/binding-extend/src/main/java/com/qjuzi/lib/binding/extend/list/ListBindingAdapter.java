/*
 * Copyright (C) 2018 Godya. All Rights Reserved.
 */
package com.qjuzi.lib.binding.extend.list;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qjuzi.lib.binding.extend.common.BindingItemModel;

public class ListBindingAdapter<T extends BindingItemModel> extends BaseAdapter {

    private LayoutInflater inflater;
    private ObservableArrayList<T> headerList;
    private ObservableArrayList<T> itemList;
    private ObservableArrayList<T> footerList;

    public ListBindingAdapter(Context context, ObservableArrayList<T> itemList) {
        this(context, itemList, null, null);
    }

    public ListBindingAdapter(Context context,
                              ObservableArrayList<T> itemList,
                              ObservableArrayList<T> headerList,
                              ObservableArrayList<T> footerList) {
        inflater = LayoutInflater.from(context);
        this.itemList = itemList;
        this.headerList = headerList;
        this.footerList = footerList;
        if (this.headerList == null) {
            this.headerList = new ObservableArrayList<>();
        }
        if (this.footerList == null) {
            this.footerList = new ObservableArrayList<>();
        }
        initNotifyChangeListener();
    }

    @Override
    public int getCount() {
        int itemSize = itemList.size();
        int headerSize = headerList.size();
        int footerSize = footerList.size();
        return itemSize + headerSize + footerSize;
    }

    @Override
    public Object getItem(int position) {
        return getItemModel(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemModel(position).layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewDataBinding dataBinding;
        BindingItemModel model = getItemModel(position);

        if (convertView == null) {
            dataBinding = DataBindingUtil.inflate(inflater, model.layoutId, parent, false);
        } else {
            dataBinding = DataBindingUtil.getBinding(convertView);
        }

        for (int index = 0; index < model.variableMap.size(); index++) {
            int key = model.variableMap.keyAt(index);
            dataBinding.setVariable(key, model.variableMap.get(key));
        }
        return dataBinding.getRoot();
    }

    private BindingItemModel getItemModel(int position) {
        int itemSize = itemList.size();
        int headerSize = headerList.size();
        int footerSize = footerList.size();

        if (headerSize > 0 && position < headerSize) {
            return headerList.get(position);
        } else if (footerSize > 0 && position >= (headerSize + itemSize)) {
            int positionInFooter = position - headerSize - itemSize;
            return footerList.get(positionInFooter);
        } else {
            return itemList.get(position - headerSize);
        }
    }

    private void initNotifyChangeListener() {
        ObservableList.OnListChangedCallback<ObservableList> listChangedCallback = new ObservableList
                .OnListChangedCallback<ObservableList>() {
            @Override
            public void onChanged(ObservableList sender) {
                notifyAndFixIndex();
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                notifyAndFixIndex();
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                notifyAndFixIndex();
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                notifyAndFixIndex();
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                notifyAndFixIndex();
            }
        };
        headerList.addOnListChangedCallback(listChangedCallback);
        itemList.addOnListChangedCallback(listChangedCallback);
        footerList.addOnListChangedCallback(listChangedCallback);
    }

    private void notifyAndFixIndex() {
        ListBindingAttr.fixIndex(headerList);
        ListBindingAttr.fixIndex(footerList);
        ListBindingAttr.fixIndex(footerList);
        notifyDataSetChanged();
    }
}