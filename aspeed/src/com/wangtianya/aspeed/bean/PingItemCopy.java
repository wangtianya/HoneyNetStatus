/*
 * Copyright (C) 2015 wangtianya.com, Inc. All Rights Reserved.
 */
package com.wangtianya.aspeed.bean;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.wangtianya.yaa.persistence.utils.FileUtil;
import com.wangtianya.yaa.persistence.utils.GsonUtil;
import com.wangtianya.aspeed.core.ASConfig;
import com.wangtianya.aspeed.core.ASContext;

import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

public class PingItemCopy {
    public static final File dbFilePath = new File(FileUtil.getAppDir() + File.separator + "official.db");
    public static File dbUserFilePath = new File(FileUtil.getAppDir() + File.separator + "user_defult.db");

    @Expose
    public int id;
    @Expose
    public String type;
    @Expose
    public String title;
    @Expose
    public String ip;
    @Expose
    public String create_time;
    @Expose
    public int seq;

    public static String getJson() {
        return GsonUtil.getUtil().toJson(PingItemCopy.getGameServers("英雄联盟"), null, true, null, null, true);
    }

    public static ArrayList<PingItemCopy> getFavouriteGameServer() {
        String sql = "select * from item";
        return getPingItems(sql, dbUserFilePath);
    }

    public static ArrayList<PingItemCopy> getGameServers(String gameName) {
        String sql = "select * from item where type='游戏' and sub_type='" + gameName + "'";
        return getPingItems(sql, dbFilePath);
    }

    public static void deleteFavouriteGameServer(PingItemCopy ns) {
        String sql = "DELETE FROM item WHERE id=" + ns.id;
        SQLiteDatabase sqLiteDatabase = getSQLiteDatabase(dbUserFilePath);
        sqLiteDatabase.execSQL(sql);
    }

    public static void insertFavouriteGameServer(PingItemCopy ns) {

        String args = "";

        args += ns.id;
        args += ",'" + ns.type + "'";
        args += ",'" + ns.title + "'";;
        args += ",'" + ns.ip + "'";;
        args += ",'" + ns.create_time + "'";;
        args += "," + ns.seq;

        String sql = "insert into item(id, type, sub_type, title, ip, create_time, seq) values("+ args +")" ;


        SQLiteDatabase sqLiteDatabase = getSQLiteDatabase(dbUserFilePath);
        try {
            sqLiteDatabase.execSQL(sql);
        } catch (SQLiteConstraintException e) {
            e.getMessage();
        }
    }


    private static ArrayList<PingItemCopy> getPingItems(String sql, File file) {
        ArrayList<PingItemCopy> nss = new ArrayList<PingItemCopy>();

        SQLiteDatabase sqLiteDatabase = getSQLiteDatabase(file);

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (!cursor.moveToFirst()) {
            return null;
        }
        addPingItem(cursor, nss);

        while (cursor.moveToNext()) {
            addPingItem(cursor, nss);
        }
        return nss;
    }

    private static void addPingItem(Cursor cursor, ArrayList<PingItemCopy> nss) {
        PingItemCopy item = new PingItemCopy();
        item.id = cursor.getInt(cursor.getColumnIndex("id"));
        item.type = cursor.getString(cursor.getColumnIndex("type"));
        item.ip = cursor.getString(cursor.getColumnIndex("ip"));
        item.title = cursor.getString(cursor.getColumnIndex("title"));
        item.create_time = cursor.getString(cursor.getColumnIndex("create_time"));
        item.seq = cursor.getInt(cursor.getColumnIndex("seq"));
        nss.add(item);
    }

    
    
    
    
    
    
    
    
    
    
    
    

    /**
     * 1 先查看本地是否有，没有先初始化
     * 2 获取本地最新的版本号
     * 3 获取服务器最新的版本号
     * 4 比较，如果服务器比本地新会下载覆盖
     */
    public static void initDB() {
        if (ASConfig.Common.isDeBug) {
            if (dbFilePath.exists()){
                dbFilePath.delete();
            }
            if (dbUserFilePath.exists()){
                dbUserFilePath.delete();
            }
        }

        if (!dbFilePath.exists()) {
            InputStream is = null;
            try {
                is = ASContext.getContext().getResources().getAssets().open("official.db");
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileUtil.write(is, dbFilePath);
        }

        if (!dbUserFilePath.exists()) {
            InputStream is = null;
            try {
                is = ASContext.getContext().getResources().getAssets().open("user_base.db");
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileUtil.write(is, dbUserFilePath);
        }

        // 1 TODO 获取本地最新的版本号

        // 2 TODO 获取服务器最新的版本号
    }

    public static SQLiteDatabase getSQLiteDatabase(File filePath) {
        SQLiteDatabase sqLiteDatabase = null;
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(filePath, null);
        return sqLiteDatabase;
    }

    private static int getLocalDBVersion() {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbFilePath, null);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from version", null);
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("version"));
    }
}
