package com.wangtianya.aspeed.wigets.topbar;

import com.wangtianya.yaa.core.util.ScreenUtil;
import com.wangtianya.aspeed.R;
import com.wangtianya.aspeed.core.ASContext;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TopBarView extends LinearLayout {

    private LinearLayout llTopBar;

    private TextView tvTitle;

    private ImageView btnLeft;

    private ImageView btnAdd, btnSearch, btnRefresh, btnSetting;

    private LinearLayout container;

    private Context mContext;

    int dp40 = ScreenUtil.dip2px(40, ASContext.getContext());
    int dp5 = ScreenUtil.dip2px(5, ASContext.getContext());
    int dp10 = ScreenUtil.dip2px(10, ASContext.getContext());
    int dp30 = ScreenUtil.dip2px(30, ASContext.getContext());

    public TopBarView(Context con, AttributeSet attrs) {
        super(con, attrs);
        mContext = con;
        init();
    }

    public void init() {

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        llTopBar = (LinearLayout) mInflater.inflate(R.layout.self_topbar, null);
        addView(llTopBar, new LayoutParams(LayoutParams.MATCH_PARENT, dp40));

        tvTitle = (TextView) llTopBar.findViewById(R.id.tvTitle);

        btnLeft = (ImageView) llTopBar.findViewById(R.id.btnLeft);

        container = (LinearLayout) llTopBar.findViewById(R.id.llBtnRightContainer);

        btnRefresh = addRightButtonView(R.drawable.topbar_icon_button_refresh);
        btnAdd = addRightButtonView(R.drawable.topbar_icon_button_add);
        btnSetting = addRightButtonView(R.drawable.topbar_icon_button_setting);
        btnSearch = addRightButtonView(R.drawable.topbar_icon_button_search);

    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public String getTitle() {
        return tvTitle.getText().toString();
    }

    public void setLeftButton(LeftButtonType type, OnClickListener listener) {
        switch (type) {
            case List:
                btnLeft.setImageDrawable(getResources().getDrawable(R.drawable.topbar_icon_button_list));
                btnLeft.setOnClickListener(listener);
                break;
            case Back:
                btnLeft.setImageDrawable(getResources().getDrawable(R.drawable.topbar_icon_button_back));
                btnLeft.setOnClickListener(listener);
                break;
            default:
                break;
        }
    }

    public void showRightButton(RightButtonType type, OnClickListener listener) {
        switch (type) {
            case Add:
                btnAdd.setVisibility(View.VISIBLE);
                btnAdd.setOnClickListener(listener);
                break;
            case Search:
                btnSearch.setVisibility(View.VISIBLE);
                btnSearch.setOnClickListener(listener);
                break;
            case Refresh:
                btnRefresh.setVisibility(View.VISIBLE);
                btnRefresh.setOnClickListener(listener);
                break;
            case Setting:
                btnSetting.setVisibility(View.VISIBLE);
                btnSetting.setOnClickListener(listener);
                break;
            default:
                break;
        }
    }

    public void hideRightButton(RightButtonType type) {
        switch (type) {
            case Add:
                btnAdd.setVisibility(View.GONE);
                break;
            case Search:
                btnSearch.setVisibility(View.GONE);
                break;
            case Refresh:
                btnRefresh.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    public void hideAllRightButton() {
        btnAdd.setVisibility(View.GONE);
        btnSearch.setVisibility(View.GONE);
        btnRefresh.setVisibility(View.GONE);
    }

    public static enum LeftButtonType {
        List, Back
    }

    public static enum RightButtonType {
        Add, Search, Refresh, Setting
    }

    private ImageView addRightButtonView(int id) {
        ImageView iv = new ImageView(getContext());
        iv.setImageDrawable(getResources().getDrawable(id));
        iv.setPadding(dp5, dp5, dp5 + dp10, dp5);
        iv.setVisibility(View.GONE);
        container.addView(iv, new LayoutParams(dp30 + dp10, dp30));
        return iv;
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }
}
