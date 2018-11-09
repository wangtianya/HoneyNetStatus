
package com.qjuzi.yaa.persistence.preference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

@Deprecated
public class YaaPreference {

    private SharedPreferences mSharedPreferences;

    public static YaaPreference fromActivity(Activity activity) {
        return new YaaPreference(activity, Context.MODE_PRIVATE);
    }

    public static YaaPreference fromApp(Context context) {
        return new YaaPreference(context, Context.MODE_PRIVATE);
    }

    public static YaaPreference fromName(String name, Context context) {
        return new YaaPreference(name, context, Context.MODE_PRIVATE);
    }

    private YaaPreference(Activity activity, int mode) {
        mSharedPreferences = activity.getPreferences(mode);
    }

    private YaaPreference(Context context, int mode) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private YaaPreference(String name, Context context, int mode) {
        mSharedPreferences = context.getSharedPreferences(name, mode);
    }

    private SharedPreferences getPreferneces() {
        return mSharedPreferences;
    }

    /**
     * 打印所有
     */
    public void print() {
        System.out.println(getPreferneces().getAll());
    }

    /**
     * 清空保存在默认SharePreference下的所有数据
     */
    public void clear() {
        getPreferneces().edit().clear().commit();
    }

    /**
     * 保存字符串
     *
     * @return
     */
    public void put(String key, String value) {
        getPreferneces().edit().putString(key, value).apply();
    }

    /**
     * 读取字符串
     *
     * @param key
     *
     * @return
     */
    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defValue) {
        return getPreferneces().getString(key, defValue);
    }

    /**
     * 保存整型值
     *
     * @return
     */
    public void put(String key, int value) {
        getPreferneces().edit().putInt(key, value).apply();
    }

    /**
     * 读取整型值
     *
     * @param key
     *
     * @return
     */
    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defValue) {
        return getPreferneces().getInt(key, defValue);
    }

    public void put(String key, long value) {
        getPreferneces().edit().putLong(key, value).apply();
    }

    public long getLong(String key) {
        return getLong(key, 0L);
    }

    public Long getLong(String key, Long defValue) {
        return getPreferneces().getLong(key, defValue);
    }

    /**
     * 保存布尔值
     *
     * @return
     */
    public void put(String key, boolean bool) {
        getPreferneces().edit().putBoolean(key, bool).apply();
    }

    /**
     * t 读取布尔值
     *
     * @param key
     *
     * @return
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return getPreferneces().getBoolean(key, defValue);
    }

    /**
     * 移除字段
     *
     * @return
     */
    public void removeString(String key) {
        getPreferneces().edit().remove(key).apply();
    }

}