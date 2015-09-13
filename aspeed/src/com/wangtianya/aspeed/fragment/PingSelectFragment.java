/*
 * Copyright (C) 2015 wangtianya.com, Inc. All Rights Reserved.
 */
package com.wangtianya.aspeed.fragment;

import com.wangtianya.abase.core.activity.ABaseFragment;
import com.wangtianya.abase.core.context.ABaseLog;
import com.wangtianya.aspeed.R;
import com.wangtianya.aspeed.adapter.GamesPingAdapter;
import com.wangtianya.aspeed.bean.PingModel;
import com.wangtianya.aspeed.core.ASContext;
import com.wangtianya.aspeed.event.PageSwitchEvent;
import com.wangtianya.aspeed.wigets.topbar.TopBarView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class PingSelectFragment extends ABaseFragment {

    ABaseLog log = ABaseLog.getLogger();

    private ListView myListView;

    public GamesPingAdapter mPingAdapter = null;

    View mDialogView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GamesPingAdapter.isSelect = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ping, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);

        myListView = (ListView) view.findViewById(R.id.lv_my_list);
        mPingAdapter = new GamesPingAdapter();
        myListView.setAdapter(mPingAdapter);
        mPingAdapter.setmItemDatas(PingModel.getPingItemsCommand());

        initTopBar();

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPingAdapter.onItemClick(position);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public boolean onBackPressed() {
        mPingAdapter.clearCache();
        GamesPingAdapter.isSelect = false;
        PageSwitchEvent.gotoPage(PageSwitchEvent.DELAY, PageSwitchEvent.TransitionAnimation.Back);
        return false;

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    public void initTopBar() {
        ASContext.Caches.topbar.hideAllRightButton();
        ASContext.Caches.topbar.setLeftButton(TopBarView.LeftButtonType.Back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
