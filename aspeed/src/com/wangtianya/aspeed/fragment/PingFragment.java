/*
 * Copyright (C) 2015 wangtianya.com, Inc. All Rights Reserved.
 */
package com.wangtianya.aspeed.fragment;

import com.wangtianya.yaa.core.activity.YaaFragment;
import com.wangtianya.yaa.core.util.ABaseLog;
import com.wangtianya.yaa.core.util.AToast;
import com.wangtianya.yaa.net.utils.NetworkUtil;
import com.wangtianya.aspeed.R;
import com.wangtianya.aspeed.adapter.GamesPingAdapter;
import com.wangtianya.aspeed.bean.PingModel;
import com.wangtianya.aspeed.core.ASContext;
import com.wangtianya.aspeed.event.PageSwitchEvent;
import com.wangtianya.aspeed.wigets.matetrialdialog.MaterialDialog;
import com.wangtianya.aspeed.wigets.topbar.TopBarView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PingFragment extends YaaFragment {

    ABaseLog log = ABaseLog.getLogger();

    private ListView myListView;

    public GamesPingAdapter mPingAdapter = null;

    View mDialogView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ping, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        myListView = (ListView) view.findViewById(R.id.lv_my_list);


        mPingAdapter = new GamesPingAdapter();
        myListView.setAdapter(mPingAdapter);
        mPingAdapter.setmItemDatas(PingModel.getPingItems());

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
    public void onDestroy() {
        mPingAdapter.clearCache();
        super.onDestroy();
    }

    public void initTopBar() {

        ASContext.Caches.topbar.showRightButton(TopBarView.RightButtonType.Add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog dialog = new MaterialDialog(ASContext.Caches.mainActivity);
                mDialogView = LayoutInflater.from(ASContext.getContext()).inflate(R.layout.fragment_games_dialog_add, null);
                dialog.setContentView(mDialogView);
                dialog.setNegativeButton("添加", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!addGame()) {
                            return;
                        }
                        dialog.dismiss();
                    }
                });
                dialog.setPositiveButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setNegativeButtonColor(ASContext.getContext().getResources().getColor(R.color.main_color_pink));
                dialog.show();
            }
        });

        ASContext.Caches.topbar.showRightButton(TopBarView.RightButtonType.Search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PingModel.getPingItemsCommand().size() == 0) {
                    AToast.show("数据拉取中, 3秒后再试!");
                    PingModel.getCommandFromInternet();
                    return;
                }
                PageSwitchEvent.gotoPage(PageSwitchEvent.DELAY_SELECT, PageSwitchEvent.TransitionAnimation.Go);
            }
        });
    }

    private boolean addGame() {
        TextView etTitle = (TextView) mDialogView.findViewById(R.id.et_title);
        TextView etIp = (TextView) mDialogView.findViewById(R.id.et_ip);

        String title = etTitle.getText().toString();
        if (TextUtils.isEmpty(title)) {
            AToast.show("请输入服务器名称");
            return false;
        }

        String ip = etIp.getText().toString();
        if (!NetworkUtil.isIpv4(ip)) {
            AToast.show("IP地址不正确");
            return false;
        }
        try {
            for (PingModel tempNS : ASContext.Caches.pingModels) {
                if (tempNS.ip.equals(ip)) {
                    AToast.show("已存在此服务器");
                    return false;
                }
            }
        } catch (NullPointerException e) {
            log.d("空收藏");
        }
        PingModel.addPingItem("自定义", etIp.getText().toString(), etTitle.getText().toString(), 0);

        mPingAdapter.setmItemDatas(PingModel.getPingItems());
        return true;
    }


}
