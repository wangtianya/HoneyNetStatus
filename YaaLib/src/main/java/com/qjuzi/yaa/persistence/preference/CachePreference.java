package com.qjuzi.yaa.persistence.preference;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

public class CachePreference {

    private final SharedPreferences preferences;
    private final Map<String, Object>     memoryCache     = new ConcurrentHashMap<>(64);

    private final Map<String, JSONObject> memoryCacheJSON = new ConcurrentHashMap<>(); // 减少json和String转换耗时

    private static final ExecutorService WORKER = Executors.newSingleThreadExecutor();

    public CachePreference(Context context, String name) {
        preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void putJSON(String key, JSONObject value) {
        if (TextUtils.isEmpty(key) || value == null) {
            return;
        }

        memoryCacheJSON.put(key, value);
        asyncPut(key, value.toString());
    }

    public JSONObject getJSON(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }

        if (memoryCacheJSON.containsKey(key)) {
            return memoryCacheJSON.get(key);
        }

        String value = getString(key, "");
        if (TextUtils.isEmpty(value)) {
            return null;
        }

        try {
            JSONObject json = new JSONObject(value);
            memoryCacheJSON.put(key, json);
            return json;
        } catch (JSONException e) {
            Log.d("CachePreference", "exception", e);
        }

        return null;
    }

    public boolean putString(String key, String value) {
        if (key != null && value != null) {
            memoryCache.put(key, value);
            asyncPut(key, value);
        }
        return false;
    }

    public String getString(String key, String defaultValue) {
        Object value = memoryCache.get(key);
        if (value instanceof String) {
            return (String) value;
        } else if (preferences.contains(key)) {
            String newValue = preferences.getString(key, defaultValue);
            memoryCache.put(key, newValue);
            return newValue;
        }
        return defaultValue;
    }

    public boolean putInt(String key, int value) {
        memoryCache.put(key, value);
        asyncPut(key, value);
        return false;
    }

    public int getInt(String key, int defaultValue) {
        Object value = memoryCache.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (preferences.contains(key)) {
            int newValue = preferences.getInt(key, defaultValue);
            memoryCache.put(key, newValue);
            return newValue;
        }
        return defaultValue;
    }

    public boolean putFloat(String key, float value) {
        memoryCache.put(key, value);
        asyncPut(key, value);
        return false;
    }

    public float getFloat(String key, float defaultValue) {
        Object value = memoryCache.get(key);
        if (value instanceof Float) {
            return (Float) value;
        } else if (preferences.contains(key)) {
            float newValue = preferences.getFloat(key, defaultValue);
            memoryCache.put(key, newValue);
            return newValue;
        }
        return defaultValue;
    }

    public boolean putBoolean(String key, boolean value) {
        memoryCache.put(key, value);
        asyncPut(key, value);
        return true;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        Object value = memoryCache.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (preferences.contains(key)) {
            boolean newValue = preferences.getBoolean(key, defaultValue);
            memoryCache.put(key, newValue);
            return newValue;
        }
        return defaultValue;
    }

    public boolean putLong(String key, long value) {
        memoryCache.put(key, value);
        asyncPut(key, value);
        return false;
    }

    public Long getLong(String key, long defaultValue) {
        Object value = memoryCache.get(key);
        if (value instanceof Long) {
            return (Long) value;
        } else if (preferences.contains(key)) {
            long newValue = preferences.getLong(key, defaultValue);
            memoryCache.put(key, newValue);
            return newValue;
        }
        return defaultValue;
    }

    public boolean contains(String key) {
        return memoryCache.containsKey(key) || preferences.contains(key);
    }

    public void clear() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                memoryCache.clear();
                preferences.edit().clear().apply();
            }
        }).start();
    }

    public boolean removeKey(String key) {
        memoryCache.remove(key);
        return preferences.edit().remove(key).commit();
    }

    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    private void asyncPut(String key, Object value) {
        WORKER.execute(new PutRunnable(key, value));
    }

    private class PutRunnable implements Runnable {

        private String key;
        private Object value;

        public PutRunnable(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public void run() {
            SharedPreferences.Editor edit = preferences.edit();
            if (value instanceof String) {
                edit.putString(key, (String) value);
            } else if (value instanceof Long) {
                edit.putLong(key, (Long) value);
            } else if (value instanceof Integer) {
                edit.putInt(key, (Integer) value);
            } else if (value instanceof Boolean) {
                edit.putBoolean(key, (Boolean) value);
            } else if (value instanceof Float) {
                edit.putFloat(key, (Float) value);
            }
            edit.apply();
        }
    }
}
