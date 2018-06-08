package cn.wangtianya.learn.recycler;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import cn.wangtianya.yaa.binding.widget.BindingAdapterItemModel;

public class YaaRecyclerView extends RecyclerView {

    LinearLayoutManager linearLayoutManager;
    BindingAdapterItemModel blankHolder;

    public YaaRecyclerView(Context context) {
        this(context, null);
    }

    public YaaRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YaaRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        View blankView = new View(getContext());
        blankView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600));
        blankView.setBackgroundColor(Color.parseColor("#333333"));

        linearLayoutManager = new LinearLayoutManager(getContext());
        this.setLayoutManager(linearLayoutManager);

        this.addView(blankView, -1);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        if (focused != null) {
            if (focused instanceof WebView || focused instanceof ListView || focused instanceof RecyclerView) {
                return;
            }
        }
        super.requestChildFocus(child, focused);
    }

    class BlankHolder extends BindingAdapterItemModel {
        public static final int LayoutID = -1000;

        public BlankHolder() {
            this.layoutId = LayoutID;
        }
    }

}