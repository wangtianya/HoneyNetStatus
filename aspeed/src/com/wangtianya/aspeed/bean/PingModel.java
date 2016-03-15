/*
 * Copyright (C) 2015 wangtianya.com, Inc. All Rights Reserved.
 */
package com.wangtianya.aspeed.bean;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.wangtianya.abase.persistence.utils.FileUtil;
import com.wangtianya.abase.persistence.utils.GsonUtil;
import com.wangtianya.aspeed.core.ASConfig;
import com.wangtianya.aspeed.core.ASContext;

public class PingModel {
    @Expose
    public String uuid;
    @Expose
    public String type;
    @Expose
    public String title;
    @Expose
    public String ip;
    @Expose
    public int seq;

    public static void firstInit() {
        String json = "[]";
        try {
            json = FileUtil.readString(ASContext.getContext().getResources().getAssets()
                    .open(ASConfig.Common.PING_ITEMS_INIT_FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<PingModel> items = GsonUtil.getUtil().fromJson(json, new TypeToken<List<PingModel>>() {
        });
        ASConfig.Pref.savePingItemJsonStr(GsonUtil.getUtil().toJson(items));
    }

    public static void commandInit(String jsonFromInternet) {
        List<PingModel> items = GsonUtil.getUtil().fromJson(jsonFromInternet, new TypeToken<List<PingModel>>() {
        });
        ASConfig.Pref.SavePingItemCommandJson(GsonUtil.getUtil().toJson(items));
    }

    /**
     * 获取本地所有
     */
    public static List<PingModel> getPingItems() {
        if (ASContext.Caches.pingModels.size() == 0) {
            ASContext.Caches.pingModels.addAll(getItemsFromPref());
        }

        Collections.sort(ASContext.Caches.pingModels, new Comparator<PingModel>() {
            @Override
            public int compare(PingModel lhs, PingModel rhs) {
                if (lhs.seq == rhs.seq) {
                    return 0;
                }
                if (lhs.seq > rhs.seq) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return ASContext.Caches.pingModels;
    }

    public static List<PingModel> getPingItemsCommand() {
        if (ASContext.Caches.pingModelCommands.size() == 0) {
            ASContext.Caches.pingModelCommands.addAll(getItemsCommandFromPref());
        }

        Collections.sort(ASContext.Caches.pingModelCommands, new Comparator<PingModel>() {
            @Override
            public int compare(PingModel lhs, PingModel rhs) {
                if (lhs.seq == rhs.seq) {
                    return 0;
                }
                if (lhs.seq > rhs.seq) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return ASContext.Caches.pingModelCommands;
    }

    private static List<PingModel> getItemsFromPref() {
        return GsonUtil.getUtil().fromJson(ASConfig.Pref.getPingItemJsonStr(), new TypeToken<List<PingModel>>() {
        });
    }

    private static List<PingModel> getItemsCommandFromPref() {
        return GsonUtil.getUtil().fromJson(ASConfig.Pref.getPingItemCommandJson(), new TypeToken<List<PingModel>>() {
        });
    }

    private static void saveCacheToPref() {
        ASConfig.Pref.savePingItemJsonStr(GsonUtil.getUtil().toJson(ASContext.Caches.pingModels));
    }

    private static void saveCommandCacheToPref() {
        ASConfig.Pref.SavePingItemCommandJson(GsonUtil.getUtil().toJson(ASContext.Caches.pingModelCommands));
    }

    public static void addPingItem(String type, String ip, String title, int seq) {
        addPingItem(null, type, ip, title, seq);
    }
    public static void addPingItem(String uuid, String type, String ip, String title, int seq) {
        PingModel item = new PingModel();
        item.uuid = UUID.randomUUID().toString();
        if (uuid != null) {
            item.uuid  = uuid;
        }
        item.type = type;
        item.ip = ip;
        item.title = title;
        item.seq = seq;
        ASContext.Caches.pingModels.add(item);
        PingModel.saveCacheToPref();
        initItem();
    }

    public static void updatePingItem(PingModel item, String type, String ip, String title, int seq) {
        item.type = type;
        item.ip = ip;
        item.title = title;
        item.seq = seq;
        PingModel.saveCacheToPref();
        initItem();
    }

    public static void initItem() {
        ASContext.Caches.pingModels.clear();
        getPingItems();
    }

    public static boolean deletePingItem(String uuid) {
        PingModel item = getItemById(uuid);
        if (item != null) {
            ASContext.Caches.pingModels.remove(item);
            saveCacheToPref();
            return true;
        }
        return false;
    }

    public static PingModel getItemById(String uuid) {
        for (PingModel item : getPingItems()) {
            if (uuid.equals(item.uuid)) {
                return item;
            }
        }
        return null;
    }

    public static StringRequest getCommandFromInternet() {
        StringRequest request = new StringRequest("http://wangtianya.github.io/aspeed/command_servers1.html", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int version = jsonObject.optInt("version");
                    if (version < 0 || version > ASConfig.Pref.getPingCommandVersion()) {
                        PingModel.commandInit(jsonObject.getString("list"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);

        ASContext.getRequestQueue().add(request);
        return request;
    }

}
