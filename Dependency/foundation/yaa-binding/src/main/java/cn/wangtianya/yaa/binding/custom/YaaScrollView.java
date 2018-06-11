package cn.wangtianya.yaa.binding.custom;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

public class YaaScrollView extends ScrollView {

    public static final Object TAG = new Object();

    private int top;
    private int middle;
    private int bottom;

    private PageStatus pageStatus = PageStatus.Chaos;
    private int currentY;

    private LinearLayout scrollLayout;
    private View blankView;
    private LinearLayout container;

    public YaaScrollView(Context context) {
        this(context, null);
    }

    public YaaScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YaaScrollView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setTag(TAG);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));

        blankView = new View(getContext());
        blankView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        blankView.setBackgroundColor(Color.GREEN);

        container = new LinearLayout(getContext());
        container.setOrientation(LinearLayout.VERTICAL);
        container.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        container.setBackgroundColor(Color.LTGRAY);

        scrollLayout = new LinearLayout(getContext());
        scrollLayout.setOrientation(LinearLayout.VERTICAL);
        scrollLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        scrollLayout.setBackgroundColor(Color.TRANSPARENT);

        scrollLayout.addView(blankView);
        scrollLayout.addView(container);
        addView(scrollLayout);
    }

    public void setHeight(int height, int bottom) {
        getLayoutParams().height = height;
        scrollLayout.getLayoutParams().height = height;

        blankView.getLayoutParams().height = height;
        container.getLayoutParams().height = height;

        this.top = blankView.getLayoutParams().height;
        this.middle = (blankView.getLayoutParams().height - bottom) / 2 + bottom;
        this.bottom = bottom;
    }

    public void fixBottom() {

    }

    private PageStatus getCurrentYStatus() {
        if (currentY == top) {
            return PageStatus.Top;
        } else if (currentY == middle) {
            return PageStatus.Mid;
        } else if (currentY == bottom) {
            return PageStatus.Bottom;
        }
        return PageStatus.Chaos;
    }

    public void addContent(View view) {
        container.addView(view);
    }

    public PageStatus getStatus() {
        return pageStatus;
    }

    public void updateStatus(PageStatus pageStatus, boolean smooth) {
        this.pageStatus = pageStatus;
        int scrollY = bottom;
        if (PageStatus.Top.equals(pageStatus)) {
            scrollY = top;
        } else if (PageStatus.Mid.equals(pageStatus)) {
            scrollY = middle;
        }
        if (smooth) {
            smoothScrollTo(0, scrollY);
        } else {
            scrollTo(0, scrollY);
        }

    }

    public boolean childCanScroll() {
        return PageStatus.Top.equals(getCurrentYStatus())
                && PageStatus.Top.equals(pageStatus);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        currentY = getScrollY();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) { // 把事件传给下面
            onTouchEvent(ev);
            return false;
        }
        return true; // 偷取了子节点的事件，子节点不再获得事件。
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            int scrollY = getScrollY();

            int lineTopMid = middle + (top - middle) / 2;
            int lineMid = middle;
            int lineMidBottom = bottom + (middle - bottom) / 2;

            if (scrollY > lineTopMid) {
                updateStatus(PageStatus.Top, true);
            } else if (scrollY > lineMid) {
                updateStatus(PageStatus.Mid, true);
            } else if (scrollY > lineMidBottom) {
                updateStatus(PageStatus.Mid, true);
            } else {
                updateStatus(PageStatus.Bottom, true);
            }
            return true;
        }

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

    public enum PageStatus {
        Top, Mid, Bottom, Chaos
    }
}