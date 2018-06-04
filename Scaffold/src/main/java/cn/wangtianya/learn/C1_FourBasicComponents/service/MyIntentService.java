package cn.wangtianya.learn.C1_FourBasicComponents.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by wangtianya on 2018/3/14.
 */

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(MyIntentService.class.getName(), "onCreate");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e(MyIntentService.class.getName(), "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(MyIntentService.class.getName(), "onBind");
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e(MyIntentService.class.getName(), intent.getAction() + "," + intent.getStringExtra("love"));
        Log.e(MyIntentService.class.getName(), "onHandleIntent");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(MyIntentService.class.getName(), "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e(MyIntentService.class.getName(), "onDestroy");
        super.onDestroy();
    }
}
