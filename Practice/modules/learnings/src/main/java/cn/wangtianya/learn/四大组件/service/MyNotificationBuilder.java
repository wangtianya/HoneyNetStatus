/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package cn.wangtianya.learn.四大组件.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import cn.wangtianya.learn.R;

public class MyNotificationBuilder extends NotificationCompat.Builder {

    public static final String DEFAULT_CHANNEL_ID = "com.wangtianya.scaffold";
    private static final String CHANNEL_NAME = "百度地图";
    private static final String CHANNEL_DESCRIPTION = "百度地图";

    private final ChannelConfig mDefaultConfig = new ChannelConfig(DEFAULT_CHANNEL_ID,
            CHANNEL_NAME,
            CHANNEL_DESCRIPTION,
            false,
            false,
            NotificationManager.IMPORTANCE_LOW);

    public MyNotificationBuilder(Context context) {
        this(context, DEFAULT_CHANNEL_ID);
    }

    public MyNotificationBuilder(Context context, String channelId) {
        this(context, channelId, null);
    }

    /**
     * Channel在创建好之后，就不能再通过代码更改设置，请谨慎处理ChannelConfig
     */
    public MyNotificationBuilder(Context context, String channelId,
                                 ChannelConfig config) {
        super(context, channelId);
        if (config == null) {
            config = mDefaultConfig;
        }
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        ensureNotificationChannel(notificationManager, channelId, config);
    }

    private void ensureNotificationChannel(NotificationManager manager, String channelId,
                                           MyNotificationBuilder.ChannelConfig channelConfig) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (manager.getNotificationChannel(channelId) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(
                        channelConfig.mChannelId,
                        channelConfig.mChannelName,
                        channelConfig.mImportance);
                notificationChannel.setDescription(channelConfig.mChannelDescription);
                notificationChannel.enableVibration(channelConfig.mSupportVibrate);
                notificationChannel.enableLights(channelConfig.mSupportLight);
                manager.createNotificationChannel(notificationChannel);
            }
        }
    }

    public static Notification getNotificationWithDefaultChannelId(Context context) {
        return new MyNotificationBuilder(context).build();
    }

    public static Notification getNotificationWithChannelId(Context context, String channelId) {
        return new MyNotificationBuilder(context, channelId).build();
    }

    /**
     * Channel在创建好之后，就不能再通过代码更改设置，请谨慎处理ChannelConfig
     */
    public class ChannelConfig {

        String mChannelId;
        String mChannelName;
        String mChannelDescription;
        boolean mSupportVibrate;
        boolean mSupportLight;
        int mImportance;

        public ChannelConfig(String channelId, String channelName, String channelDescription,
                             boolean supportVibrate, boolean supportLight, int importance) {
            mChannelId = channelId;
            mChannelName = channelName;
            mChannelDescription = channelDescription;
            mSupportVibrate = supportVibrate;
            mSupportLight = supportLight;
            mImportance = importance;
        }
    }

    public static Notification generate(Context context, String title) {
        if (TextUtils.isEmpty(title)) {
            title = "小度正在为您服务。";
        }

        MyNotificationBuilder builder = new MyNotificationBuilder(context);
        setNotifiLargeIcon(context, builder);
        return builder
                .setTicker(title)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText("百度地图")
                .build();
    }


    public static void setNotifiLargeIcon(Context ctx, NotificationCompat.Builder builder) {
        if (ctx != null && builder != null) {
            Bitmap largeIcon = BitmapFactory.decodeResource(
                    ctx.getResources(), R.drawable.ic_launcher);
            if (largeIcon != null) {
                builder.setLargeIcon(largeIcon);
            }
        }
    }
}