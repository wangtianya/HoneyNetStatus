package cn.wangtianya.yaa.binding.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import cn.wangtianya.yaa.binding.R;

/**
 * Created by wangtianya on 2018/4/20.
 */

public class BindingRecycleViewAdapterTemp<T extends BindingAdapterItemModel>
        extends RecyclerView.Adapter<BindingRecycleViewAdapterTemp.BindingHolder> {
    public static final int HEADER = -1000;
    public static final int FOOTER = -2000;

    private Context context;
    private LayoutInflater inflater;

    public LinearLayout headerContainer;
    public LinearLayout footerContainer;

    private ObservableArrayList<T> modelList;


    public BindingRecycleViewAdapterTemp(Context context, ObservableArrayList<T> modelList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.modelList = modelList;
        initNotifyChangeListener();
        initHeaderFooter();
    }

    public BindingRecycleViewAdapterTemp(Context context, ObservableArrayList<T> modelList, int layoutId,
                                         int variableId) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        for (T model : modelList) {
            model.layoutId = layoutId;
            model.variableId = variableId;
        }
        this.modelList = modelList;
        initNotifyChangeListener();
        initHeaderFooter();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        if (viewType == HEADER) {
            binding = DataBindingUtil.inflate(inflater, R.layout.container_ll, parent, false);
            removeSelf(headerContainer);
            ((ViewGroup)binding.getRoot()).addView(headerContainer);
        } else if (viewType == FOOTER) {
            binding = DataBindingUtil.inflate(inflater, R.layout.container_ll, parent, false);
            removeSelf(footerContainer);
            ((ViewGroup)binding.getRoot()).addView(footerContainer);
        } else {
            binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        }

        return new BindingHolder(binding);
    }

    // 数据绑定
    @Override
    public void onBindViewHolder(BindingRecycleViewAdapterTemp.BindingHolder holder, int position) {
        if (position != 0 && position != getItemCount() - 1) {
            holder.bindData(modelList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        }
        if (position == getItemCount() - 1) {
            return FOOTER;
        }
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
        public ViewDataBinding binding;

        public BindingHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(T model) {
            binding.setVariable(model.variableId, model);
        }

    }

    // 以下是header和footer

    private void initHeaderFooter() {
        headerContainer = new LinearLayout(context);
        headerContainer.setOrientation(LinearLayout.VERTICAL);
        headerContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT));
        footerContainer = new LinearLayout(context);
        footerContainer.setOrientation(LinearLayout.VERTICAL);
        footerContainer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT));
    }

    public void addHeader(View headerItem) {
        addToConainer(headerItem, headerContainer);
    }

    public void addHeader(View headerItem, int index) {
        if (headerItem.getLayoutParams() == null) {
            headerItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        headerContainer.addView(headerItem, index);
    }

    public void addFooter(View footerItem) {
        addToConainer(footerItem, footerContainer);
    }

    public void addFooter(View footerItem, int index) {
        if (footerItem.getLayoutParams() == null) {
            footerItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        footerContainer.addView(footerItem, index);
    }

    private void addToConainer(View item, LinearLayout container) {
        if (item.getLayoutParams() == null) {
            item.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        container.addView(item);
    }

    private void removeSelf(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
    }
}



