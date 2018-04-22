package com.wangtianya.yaa.databinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by wangtianya on 2018/4/20.
 */

public class BaseRecycleViewAdapter<T extends AdapterBindingModel>
        extends RecyclerView.Adapter<BaseRecycleViewAdapter.BindingHolder> {

    private LayoutInflater inflater;
    private ObservableArrayList<T> modelList;

    public BaseRecycleViewAdapter(Context context, ObservableArrayList<T> modelList) {
        inflater = LayoutInflater.from(context);
        this.modelList = modelList;
        initNotifyChangeListener();
    }

    public BaseRecycleViewAdapter(Context context, ObservableArrayList<T> modelList, int layoutId, int variableId) {
        inflater = LayoutInflater.from(context);
        for (T model : modelList) {
            model.layoutId = layoutId;
            model.variableId = variableId;
        }
        this.modelList = modelList;
        initNotifyChangeListener();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        return new BindingHolder(binding);
    }

    /*
    * 数据绑定
    * */
    @Override
    public void onBindViewHolder(BaseRecycleViewAdapter.BindingHolder holder, int position) {
        holder.bindData(modelList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return modelList.get(position).layoutId;
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

    class BindingHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        public BindingHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(T model) {
            binding.setVariable(model.variableId, model);
        }

    }
}



