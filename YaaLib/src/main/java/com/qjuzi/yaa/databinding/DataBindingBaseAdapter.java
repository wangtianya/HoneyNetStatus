package com.qjuzi.yaa.databinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by tianya on 2017/5/4.
 */
public class DataBindingBaseAdapter<T extends AdapterBindingModel> extends BaseAdapter {

    private LayoutInflater inflater;
    private ObservableArrayList<T> modelList;

    public DataBindingBaseAdapter(Context context, ObservableArrayList<T> modelList) {
        inflater = LayoutInflater.from(context);
        this.modelList = modelList;
        initNotifyChangeListener();
    }

    public DataBindingBaseAdapter(Context context, ObservableArrayList<T> modelList, int layoutId, int variableId) {
        inflater = LayoutInflater.from(context);
        for (T model : modelList) {
            model.layoutId = layoutId;
            model.variableId = variableId;
        }
        this.modelList = modelList;
        initNotifyChangeListener();
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewDataBinding dataBinding;
        T model = modelList.get(position);

        if (convertView == null) {
            dataBinding = DataBindingUtil.inflate(inflater, model.layoutId, parent, false);
        } else {
            dataBinding = DataBindingUtil.getBinding(convertView);
        }

        dataBinding.setVariable(model.variableId, model);
        return dataBinding.getRoot();
    }

    private void initNotifyChangeListener() {
        modelList.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
            @Override
            public void onChanged(ObservableList<T> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                notifyDataSetChanged();
            }
        });
    }
}