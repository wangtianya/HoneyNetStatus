package cn.wangtianya.yaa.binding.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class YaaListView extends ListView {

    private float lastY;
    public YaaListView(Context context) {
        super(context);
    }

    public YaaListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YaaListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN && getParentScrollView().childCanScroll()) {
            getParentScrollView().requestDisallowInterceptTouchEvent(true);
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (lastY > ev.getY()) {
                // 如果是向上滑动，且不能滑动了，则让ScrollView处理
                if (!canScrollList(1)) {
                    getParentScrollView().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            } else if (ev.getY() > lastY) {
                // 如果是向下滑动，且不能滑动了，则让ScrollView处理
                if (!canScrollList(-1)) {
                    getParentScrollView().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
            }
        }
        lastY = ev.getY();
        return super.dispatchTouchEvent(ev);
    }

    private YaaScrollView getParentScrollView() {
        return getRootView().findViewWithTag(YaaScrollView.TAG);
    }
}