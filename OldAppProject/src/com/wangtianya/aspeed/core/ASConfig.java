/*
 * Copyright (C) 2015 wangtianya.com, Inc. All Rights Reserved.
 */
package com.wangtianya.aspeed.core;

import com.wangtianya.yaa.persistence.utils.ABasePreference;

public class ASConfig {

    public static class Common {
        public static final boolean isDeBug = false;
        public static final String PING_ITEMS_INIT_FILE_NAME = "ping_items_init.json";
    }

    public static class Pref {
        private static ABasePreference pref = ABasePreference.fromApp(ASContext.getContext());

        private static final String IS_FISRT_COME_IN = "IS_FISRT_COME_IN";
        public static boolean isFirstComeIn() {
            return pref.getBoolean(IS_FISRT_COME_IN, true);
        }
        public static void disableFirstComeIn() {
            pref.put(IS_FISRT_COME_IN, false);
        }

        public static final String PING_ITEM_JSON = "PING_ITEM_JSON";
        public static String getPingItemJsonStr() {
            return pref.getString(PING_ITEM_JSON, "[]");
        }
        public static void savePingItemJsonStr(String jsonStr) {
            pref.put(PING_ITEM_JSON, jsonStr);
        }

        public static final String PING_ITEM_COMMAND_JSON = "PING_ITEM_COMMAND_JSON";
        public static String getPingItemCommandJson() {
            return pref.getString(PING_ITEM_COMMAND_JSON, "[]");
        }
        public static void SavePingItemCommandJson(String jsonStr) {
            pref.put(PING_ITEM_COMMAND_JSON, jsonStr);
        }

        public static final String PING_ITEM_COMMAND_VERSION = "PING_ITEM_COMMAND_VERSION";
        public static int getPingCommandVersion() {
            return pref.getInt(PING_ITEM_COMMAND_VERSION, -1);
        }
        public static void SavePingCommandVersion(int version) {
            pref.put(PING_ITEM_COMMAND_VERSION, version);
        }


    }


}
