/*
 * Copyright (C) 2018 Godya. All Rights Reserved.
 */
package com.qjuzi.lib.binding.extend.recycler;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.qjuzi.lib.binding.extend.common.BindingItemModel;


/**
 * Created by wangtianya on 2018/4/20.
 */

public class RecyclerBindingAdapter<T extends BindingItemModel>
        extends RecyclerView.Adapter<RecyclerBindingAdapter.BindingHolder> {

    private LayoutInflater inflater;

    private ObservableArrayList<T> headerList;
    private ObservableArrayList<T> itemList;
    private ObservableArrayList<T> footerList;

    public RecyclerBindingAdapter(Context context, ObservableArrayList<T> itemList) {
        this(context, itemList, null, null);
    }

    public RecyclerBindingAdapter(Context context,
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
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        holder.bindData(getItemModel(position));
    }

    @Override
    public int getItemCount() {
        int itemSize = itemList.size();
        int headerSize = headerList.size();
        int footerSize = footerList.size();
        return itemSize + headerSize + footerSize;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemModel(position).layoutId;
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

    private boolean isHeaderViewPos(int position) {
        int headerSize = headerList.size();
        return headerSize > 0 && position < headerSize;
    }

    private boolean isFooterViewPos(int position) {
        int itemSize = itemList.size();
        int headerSize = headerList.size();
        int footerSize = footerList.size();
        return footerSize > 0 && position >= (headerSize + itemSize);
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
        RecyclerBindingAttr.fixIndex(headerList);
        RecyclerBindingAttr.fixIndex(footerList);
        RecyclerBindingAttr.fixIndex(footerList);
        notifyDataSetChanged();
    }

    static class BindingHolder extends RecyclerView.ViewHolder  {
        ViewDataBinding binding;

        public BindingHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(BindingItemModel model) {
            for (int index = 0; index < model.variableMap.size(); index++) {
                int key = model.variableMap.keyAt(index);
                binding.setVariable(key, model.variableMap.get(key));
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isHeaderViewPos(position)) {
                        return gridLayoutManager.getSpanCount();
                    } else if (isFooterViewPos(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (spanSizeLookup != null) {
                        return spanSizeLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BindingHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }

    }
}



