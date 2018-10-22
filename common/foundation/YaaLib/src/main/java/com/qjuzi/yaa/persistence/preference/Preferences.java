package com.qjuzi.yaa.persistence.preference;

import java.util.concurrent.ConcurrentHashMap;

import com.qjuzi.yaa.context.YaaContext;

import android.content.Context;

/**
 * author: liguoqing
 */
public class Preferences extends CachePreference {

    /**
     * 默认配置文件名。
     */
    public static final String SP_NAME = "";

    private static final ConcurrentHashMap<String, Preferences> cache = new ConcurrentHashMap<String, Preferences>();

    private Preferences(Context context, String cfgName) {
        super(context, cfgName);
    }

    /**
     * 构建 Preferences 实例。
     *
     * @param cfgName 配置文件名称
     */
    public static synchronized Preferences build(Context context, String cfgName) {
        if (cfgName == null) {
            cfgName = SP_NAME;
        }

        if (context == null) {
            context = YaaContext.getContext();
        }

        if (cache.containsKey(cfgName)) {
            return cache.get(cfgName);
        } else {
            Preferences preferences = new Preferences(context, cfgName);
            cache.put(cfgName, preferences);
            return preferences;
        }
    }

    /**
     * 使用默认名称("map_pref") 构建 Preferences 实例。
     */
    public static Preferences build(Context context) {
        return build(context, SP_NAME);
    }

}
