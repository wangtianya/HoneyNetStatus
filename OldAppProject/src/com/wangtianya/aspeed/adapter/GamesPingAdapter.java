/*
 * Copyright (C) 2015 wangtianya.com, Inc. All Rights Reserved.
 */
package com.wangtianya.aspeed.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.android.volley.Request;
import com.android.volley.Response;
import com.wangtianya.yaa.core.util.YaaLog;
import com.wangtianya.yaa.net.provider.isp.ISPModel;
import com.wangtianya.yaa.net.provider.isp.TaobaoISPRequest;
import com.wangtianya.yaa.net.ping.PingManager;
import com.wangtianya.yaa.net.ping.model.PingListener;
import com.wangtianya.yaa.net.ping.model.PingResult;
import com.wangtianya.yaa.net.ping.model.PingResultRow;
import com.wangtianya.aspeed.R;
import com.wangtianya.aspeed.bean.PingModel;
import com.wangtianya.aspeed.core.ASContext;
import com.wangtianya.aspeed.widget.matetrialdialog.MaterialDialog;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GamesPingAdapter extends BaseAdapter {
    YaaLog log = YaaLog.getLogger();

    private Map<String, GameStat> mGameStats = new HashMap<String, GameStat>();

    private Map<Integer, View> mItemViews = new HashMap<Integer, View>();
    private ArrayList<PingModel> mItemDatas = new ArrayList<PingModel>();
    private Map<Integer, PingItemViewHolder> mItemViewHolders = new HashMap<Integer, PingItemViewHolder>();

    public static boolean isSelect = false;

    public void setmItemDatas(List<PingModel> datas) {
        PingManager.getInstance().cancelAll();
        mItemDatas.clear();
        mGameStats.clear();
        mItemViews.clear();
        if (datas != null) {
            mItemDatas.addAll(datas);
        }
        this.notifyDataSetChanged();
    }

    public void clearCache() {
        PingManager.getInstance().cancelAll();
        mGameStats.clear();
        mItemViews.clear();
    }

    @Override
    public int getCount() {
        return mItemDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PingItemViewHolder viewHolder = null;
        if (mItemViews.get(position) == null) {
            LayoutInflater inflate = LayoutInflater.from(ASContext.getContext());
            convertView = inflate.inflate(R.layout.list_item_ping, null);
            mItemViews.put(position, convertView);
            viewHolder = new PingItemViewHolder();
            mItemViewHolders.put(position, viewHolder);
            viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.ll);

            LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.ll);
            viewHolder.ivDelay = (ImageView) convertView.findViewById(R.id.iv_delay);
            viewHolder.game = (TextView) convertView.findViewById(R.id.tvGame);
            viewHolder.id = (TextView) convertView.findViewById(R.id.tvId);
            viewHolder.server = (TextView) convertView.findViewById(R.id.tvServer);
            viewHolder.ip = (TextView) convertView.findViewById(R.id.tvIp);
            viewHolder.ips = (TextView) convertView.findViewById(R.id.tvIPS);
            viewHolder.netStatus = (ImageView) convertView.findViewById(R.id.ivNetStatus);
            viewHolder.delay = (TextView) convertView.findViewById(R.id.tvDelay);
            viewHolder.lossRate = (TextView) convertView.findViewById(R.id.tvLossRate);
            viewHolder.ivHeart = (ImageView) convertView.findViewById(R.id.ivHeart);

            convertView.setTag(viewHolder);
            viewHolder = (PingItemViewHolder) convertView.getTag();

            viewHolder.id.setText(String.valueOf(mItemDatas.get(position).uuid));
            viewHolder.game.setText(mItemDatas.get(position).type);
            viewHolder.server.setText(mItemDatas.get(position).title);

            viewHolder.ip.setText(mItemDatas.get(position).ip.split(",")[0]);

            if ("英雄联盟".equals(mItemDatas.get(position).type)) {
                viewHolder.ivDelay.setImageDrawable(
                        ASContext.getContext().getResources().getDrawable(R.drawable.icon_game_lol));
            }

            PingItemViewHolder tempNSItem = viewHolder;
            PingManager.getInstance()
                    .ping(mItemDatas.get(position).ip, 10000, new PingListenerImpl(tempNSItem, position));

            List<PingModel> pingModels = PingModel.getPingItems();
            if (pingModels != null) {
                for (PingModel pingModel : pingModels) {
                    if (mItemDatas.get(position).uuid.equals(pingModel.uuid)) {
                        viewHolder.ivHeart.setImageDrawable(
                                ASContext.getContext().getResources().getDrawable(R.drawable.icon_heart_full));
                    }
                }
            }
        }

        convertView = mItemViews.get(position);

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void remove(int position) {
        // cancel progress
        for (String ip : mItemDatas.get(position).ip.split(",")) {
            PingManager.getInstance().cancel(ip);
        }
        String uuidWillDelete = mItemDatas.get(position).uuid;
        mGameStats.remove(uuidWillDelete);
        mItemViews.remove(Integer.valueOf(position));
        mItemDatas.remove(position);

        this.notifyDataSetChanged();
    }

    public void onItemClick(final int position) {
        final MaterialDialog dialog = new MaterialDialog(ASContext.Caches.mainActivity);
        List<PingModel> pingModels = PingModel.getPingItems();
        for (PingModel pingModel : pingModels) {
            if (pingModel.uuid.equals(mItemDatas.get(position).uuid)) {
                dialog.setTitle("提示");
                dialog.setMessage("从我的列表中删去");
                dialog.setNegativeButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        mItemViewHolders.get(Integer.valueOf(position)).ivHeart.setImageDrawable(
                                ASContext.getContext().getResources().getDrawable(R.drawable.icon_heart_blank));
                        String uuid = mItemDatas.get(position).uuid;
                        if (!isSelect) {
                            remove(position);
                        }
                        PingModel.deletePingItem(uuid);

                    }
                });
                dialog.setPositiveButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setNegativeButtonColor(ASContext.getContext().getResources().getColor(
                        R.color.main_color_pink));
                dialog.show();
                return;
            }
        }
        dialog.setTitle("提示");
        dialog.setMessage("添加到 我的列表");
        dialog.setNegativeButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mItemViewHolders.get(Integer.valueOf(position)).ivHeart.setImageDrawable(
                        ASContext.getContext().getResources().getDrawable(R.drawable.icon_heart_full));
                PingModel.addPingItem(mItemDatas.get(position).uuid, mItemDatas.get(position).type, mItemDatas.get(position).ip,
                        mItemDatas.get(position).title, mItemDatas.get(position).seq);
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

    class PingItemViewHolder {
        LinearLayout ll;
        TextView id;
        TextView game;
        TextView server;
        TextView ip;
        TextView ips;
        ImageView netStatus;
        TextView delay;
        TextView lossRate;
        ImageView ivHeart;
        ImageView ivDelay;
    }

    class PingListenerImpl implements PingListener {

        private PingItemViewHolder mViewHolder;
        private int mPosition;

        private boolean mIsCancel = false;

        private boolean isBlocked = false;

        int i = 0;

        public PingListenerImpl(PingItemViewHolder viewHolder, int position) {
            this.mViewHolder = viewHolder;
            mPosition = position;
        }

        @Override
        public void onStart() {
            System.out.println("onStart");
            Timer timer = new Timer();
            isBlocked = true;
            TimerTask task = new TimerTask() {
                public void run() {
                    if (isBlocked == true) {
                        onFinish(null);
                    }
                }
            };
            timer.schedule(task, 5 * 1000);

            Response.Listener<ISPModel> listener = new Response.Listener<ISPModel>() {
                @Override
                public void onResponse(ISPModel response) {
                    String strWillshow = "";
                    if (!TextUtils.isEmpty(response.city)) {
                        strWillshow = response.city;
                        if ("市".equals(strWillshow.substring(strWillshow.length() - 1, strWillshow.length()))) {
                            strWillshow = strWillshow.substring(0, strWillshow.length() - 1);
                        }
                    }
                    if (!TextUtils.isEmpty(response.isp)) {
                        strWillshow += response.isp;
                    }
                    if (!TextUtils.isEmpty(strWillshow)) {
                        mViewHolder.ips.setText("(" + strWillshow + ")");
                        mViewHolder.ips.setVisibility(View.VISIBLE);
                    }
                }
            };

            Request request = new TaobaoISPRequest(mViewHolder.ip.toString(), null, null);

        }

        @Override
        public void onProgress(PingResultRow row) {
            isBlocked = false;
            i++;
            if (row == null) {
                mViewHolder.delay.setText(String.valueOf("丢包"));
                mViewHolder.netStatus.setImageDrawable(ASContext.getContext().getResources().getDrawable(R.drawable
                        .icon_delay_gray));
                updateGameStat(row);
            } else {
                mViewHolder.delay.setText(String.valueOf(row.time) + " 毫秒");

                if (row.time < 80 && row.time > 0) {
                    mViewHolder.netStatus.setImageDrawable(ASContext.getContext().getResources().getDrawable(R.drawable
                            .icon_delay_green));
                } else if (row.time < 200) {
                    mViewHolder.netStatus.setImageDrawable(ASContext.getContext().getResources().getDrawable(R.drawable
                            .icon_delay_yellow));
                } else {
                    mViewHolder.netStatus.setImageDrawable(ASContext.getContext().getResources().getDrawable(R.drawable
                            .icon_delay_red));
                }

                updateGameStat(row);
                int lossRate = (mGameStats.get(mItemDatas.get(mPosition).uuid).loss / mGameStats.get(mItemDatas.get
                        (mPosition).uuid)
                        .count) * 100;
                mViewHolder.lossRate.setText(lossRate + " %");
            }
            notifyDataSetChanged();
        }

        @Override
        public void onFinish(PingResult result) {
            if (result == null || !result.isSuccess) {
                mViewHolder.delay.setText(String.valueOf("关闭"));
                mViewHolder.netStatus.setImageDrawable(ASContext.getContext().getResources().getDrawable(R.drawable
                        .icon_delay_gray));
            }
        }

        private void updateGameStat(PingResultRow row) {
            if (mItemDatas.get(mPosition) != null && !mGameStats.containsKey(mItemDatas.get(mPosition).uuid)) {
                GameStat gs = new GameStat();
                gs.id = mItemDatas.get(mPosition).uuid;
                mGameStats.put(mItemDatas.get(mPosition).uuid, gs);
            }
            GameStat gs = mGameStats.get(mItemDatas.get(mPosition).uuid);

            if (row == null) {
                gs.loss++;
            } else {
                gs.msTotal += row.time;
            }

            gs.count++;
        }
    }

    class GameStat {
        public String id;

        public int msTotal = 0;

        public int loss = 0;

        public int count = 0;
    }
}