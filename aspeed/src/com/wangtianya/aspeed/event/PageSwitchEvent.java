/*
 * Copyright (C) 2015 wangtianya.com, Inc. All Rights Reserved.
 */
package com.wangtianya.aspeed.event;

import java.util.HashMap;
import java.util.Map;

import com.wangtianya.abase.core.activity.ABaseFragment;
import com.wangtianya.aspeed.fragment.BroadWidthFragment;
import com.wangtianya.aspeed.fragment.PingFragment;
import com.wangtianya.aspeed.fragment.PingSelectFragment;

import de.greenrobot.event.EventBus;

/**
 * Created by wangtianya on 15/9/6.
 */
public class PageSwitchEvent {
    public static final String STATUS = "状态"; // 基本状态
    public static final String ARTICLE = "资讯/科普"; // 资讯,科普
    public static final String DELAY = "测延迟"; // 延迟
    public static final String DELAY_SELECT = "推荐服务器"; // 推荐服务器
    public static final String SPEED = "测带宽"; // 网速
    public static final String WEBPAGE = "测网页"; // 网页

    public static enum TransitionAnimation{
        Go, Back
    }

    private static Map<String, Class<?>> classMap = new HashMap<>();
    static {
        classMap.put(STATUS, null);
        classMap.put(ARTICLE, null);
        classMap.put(DELAY, PingFragment.class);
        classMap.put(DELAY_SELECT, PingSelectFragment.class);
        classMap.put(SPEED, BroadWidthFragment.class);
        classMap.put(WEBPAGE, null);
    }


    public static void gotoPage(String pageName) {
        gotoPage(pageName, null);
    }

    public static void gotoPage(String pageName, TransitionAnimation transitionAnimation) {
        try {
            ABaseFragment fragment = (ABaseFragment) classMap.get(pageName).newInstance();
            EventBus.getDefault().post(new PageSwitchEvent(pageName, fragment, transitionAnimation));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // 实例方法开始
    public String pageName = "";
    public ABaseFragment fragment = null;
    public TransitionAnimation transitionAnimation;

    public PageSwitchEvent() {};

    public PageSwitchEvent(String pageName, ABaseFragment fragment, TransitionAnimation transitionAnimation) {
        this.pageName = pageName;
        this.fragment = fragment;
        this.transitionAnimation = transitionAnimation;
    }
}
