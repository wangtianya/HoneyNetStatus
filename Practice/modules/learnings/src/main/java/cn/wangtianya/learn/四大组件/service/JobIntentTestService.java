/*
 * Copyright (C) 2018 Godya. All Rights Reserved.
 */
package cn.wangtianya.learn.四大组件.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.JobIntentService;
import android.util.Log;

/**
 * Created by wangtianya on 2018/3/14.
 */

public class JobIntentTestService extends JobIntentService {
    private static final int JOB_ID = 10000002;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, JobIntentTestService.class, JOB_ID, work);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(JobIntentTestService.class.getName(), "onCreate");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e(JobIntentTestService.class.getName(), "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(JobIntentTestService.class.getName(), "onBind");
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(JobIntentTestService.class.getName(), "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e(JobIntentTestService.class.getName(), "onDestroy");

        super.onDestroy();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.e(JobIntentTestService.class.getName(), intent.getAction() + "," + intent.getStringExtra("love"));
        Log.e(JobIntentTestService.class.getName(), "onHandleIntent");
    }
}
