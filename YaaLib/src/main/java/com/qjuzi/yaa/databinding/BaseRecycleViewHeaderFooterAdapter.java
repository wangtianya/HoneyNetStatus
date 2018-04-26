package com.qjuzi.yaa.databinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by wangtianya on 2018/4/20.
 */

public class BaseRecycleViewHeaderFooterAdapter<T extends AdapterBindingModel>
        extends RecyclerView.Adapter<BaseRecycleViewHeaderFooterAdapter.BindingHolder> {

    private LayoutInflater inflater;

    private ObservableArrayList<T> headerList;
    private ObservableArrayList<T> itemList;
    private ObservableArrayList<T> footerList;


    public BaseRecycleViewHeaderFooterAdapter(Context context, ObservableArrayList<T> itemList) {
        this(context, itemList, null, null);
    }

    public BaseRecycleViewHeaderFooterAdapter(Context context,
                                              ObservableArrayList<T> itemList,
                                              ObservableArrayList<T> headerList,
                                              ObservableArrayList<T> footerList) {
        inflater = LayoutInflater.from(context);
        this.itemList = itemList;
        this.headerList = headerList;
        this.footerList = footerList;

        initNotifyChangeListener();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        return new BindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(BaseRecycleViewHeaderFooterAdapter.BindingHolder holder, int position) {
        holder.bindData(getItemModel(position));
    }

    @Override
    public int getItemCount() {
        int itemSize = itemList.size();
        int headerSize = headerList == null ? 0 :headerList.size();
        int footerSize = footerList == null ? 0 :footerList.size();
        return itemSize + headerSize + footerSize;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemModel(position).layoutId;
    }

    private T getItemModel(int position) {
        int itemSize = itemList.size();
        int headerSize = headerList == null ? 0 :headerList.size();
        int footerSize = footerList == null ? 0 :footerList.size();

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
        int headerSize = headerList == null ? 0 :headerList.size();
        return headerSize > 0 && position < headerSize;
    }

    private boolean isFooterViewPos(int position) {
        int itemSize = itemList.size();
        int headerSize = headerList == null ? 0 :headerList.size();
        int footerSize = footerList == null ? 0 :footerList.size();
        return footerSize > 0 && position >= (headerSize + itemSize);
    }

    private void initNotifyChangeListener() {
        ObservableList.OnListChangedCallback<ObservableList<T>> listChangedCallback = new ObservableList
                .OnListChangedCallback<ObservableList<T>>() {
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
        };

        headerList.addOnListChangedCallback(listChangedCallback);
        itemList.addOnListChangedCallback(listChangedCallback);
        footerList.addOnListChangedCallback(listChangedCallback);
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
                    if (spanSizeLookup != null)
                        return spanSizeLookup.getSpanSize(position);
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BaseRecycleViewHeaderFooterAdapter.BindingHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }

    }
}



