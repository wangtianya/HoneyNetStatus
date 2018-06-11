package cn.wangtianya.yaa.binding.custom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class YaaRecyclerView1 extends RecyclerView {

    private float lastY;

    public YaaRecyclerView1(Context context) {
        super(context);
    }

    public YaaRecyclerView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YaaRecyclerView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getParentScrollView().childCanScroll()) {
            iDoScroll();
        } else {
            faterDoScroll();
            return false;
        }
            if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (lastY > ev.getY() && !canScrollVertically(1)) {// 如果是向上滑动，且不能滑动了，则让ScrollView处理
                faterDoScroll();
                return false;
            } else if (ev.getY() > lastY && !canScrollVertically(-1)) { // 如果是向下滑动，且不能滑动了，则让ScrollView处理
                faterDoScroll();
                return false;
            }
        }
        lastY = ev.getY();
        return super.dispatchTouchEvent(ev);
    }

    private void faterDoScroll() {
        getParentScrollView().requestDisallowInterceptTouchEvent(false);
    }

    private void iDoScroll() {
        getParentScrollView().requestDisallowInterceptTouchEvent(true); // 传
    }

    private YaaScrollView getParentScrollView() {
        return getRootView().findViewWithTag(YaaScrollView.TAG);
    }
}