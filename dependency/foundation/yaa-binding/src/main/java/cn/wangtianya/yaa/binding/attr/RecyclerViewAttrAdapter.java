package cn.wangtianya.yaa.binding.attr;

import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import cn.wangtianya.yaa.binding.widget.BindingRecycleViewAdapter;
import cn.wangtianya.yaa.binding.widget.BindingRecycleViewEnhanceAdapter;
import cn.wangtianya.yaa.binding.widget.BindingRecycleViewHeaderFooterAdapter;

/**
 * Created by tianya on 2017/6/28.
 */
public class RecyclerViewAttrAdapter {

    @BindingAdapter({"android:adapter", "android:gridSpan"})
    public static void setAdapter(RecyclerView view, BindingRecycleViewAdapter adapter, int span) {
        view.setLayoutManager(new GridLayoutManager(view.getContext(), span));
        view.setAdapter(adapter);
    }

    @BindingAdapter({"android:adapter", "android:gridSpan"})
    public static void setAdapter(RecyclerView view, BindingRecycleViewHeaderFooterAdapter adapter, int span) {
        view.setLayoutManager(new GridLayoutManager(view.getContext(), span));
        view.setAdapter(adapter);
    }

    @BindingAdapter({"android:adapter", "android:gridSpan"})
    public static void setAdapter(RecyclerView view, BindingRecycleViewEnhanceAdapter adapter, int span) {
        view.setLayoutManager(new GridLayoutManager(view.getContext(), span));
        view.setAdapter(adapter);
    }

    @BindingAdapter({"android:adapter", "android:orientation"})
    public static void setAdapter1(RecyclerView view, BindingRecycleViewAdapter adapter, int orientation) {
        view.setLayoutManager(new LinearLayoutManager(view.getContext(), orientation, false));
        view.setAdapter(adapter);
    }

    @BindingAdapter({"android:adapter", "android:orientation"})
    public static void setAdapter1(RecyclerView view, BindingRecycleViewHeaderFooterAdapter adapter, int orientation) {
        view.setNestedScrollingEnabled(false);
        view.setLayoutManager(new LinearLayoutManager(view.getContext(), orientation, false));
        view.setAdapter(adapter);
    }

    @BindingAdapter({"android:adapter", "android:orientation"})
    public static void setAdapter1(RecyclerView view, BindingRecycleViewEnhanceAdapter adapter, int orientation) {
        view.setNestedScrollingEnabled(false);
        view.setLayoutManager(new LinearLayoutManager(view.getContext(), orientation, false));
        view.setAdapter(adapter);
    }
}
