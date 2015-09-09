/*
 * Copyright (C) 2015 wangtianya.com, Inc. All Rights Reserved.
 */
package com.wangtianya.aspeed.event;

import java.util.HashMap;
import java.util.Map;

import com.wangtianya.aspeed.fragment.BroadWidthFragment;

import android.app.Fragment;
import de.greenrobot.event.EventBus;

/**
 * Created by wangtianya on 15/9/6.
 */
public class PageSwitchEvent {
    public static final String STATUS = "状态"; // 基本状态
    public static final String ARTICLE = "资讯/科普"; // 资讯,科普
    public static final String DELAY = "测延迟"; // 延迟
    public static final String SPEED = "测带宽"; // 网速
    public static final String WEBPAGE = "测网页"; // 网页
    private static Map<String, Class<?>> classMap = new HashMap<>();
    static {
        classMap.put(STATUS, null);
        classMap.put(ARTICLE, null);
        classMap.put(DELAY, null);
        classMap.put(SPEED, BroadWidthFragment.class);
        classMap.put(WEBPAGE, null);
    }


    public static void gotoPage(String pageName) {
        try {
            Fragment fragment = (Fragment) classMap.get(pageName).newInstance();
            EventBus.getDefault().post(new PageSwitchEvent(pageName, fragment));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // 实例方法开始
    public String pageName = "";
    public Fragment fragment = null;

    public PageSwitchEvent() {};

    public PageSwitchEvent(String pageName, Fragment fragment) {
        this.pageName = pageName;
        this.fragment = fragment;
    }
}
