package cn.wangtianya.learn.四大组件.service;

import com.qjuzi.architecure.base.context.ContextCache;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import cn.wangtianya.learn.R;

/**
 * Created by wangtianya on 2018/3/14.
 */

public class LearnService extends Service {

    @Override
    public void onCreate() {
        Log.e(this.getClass().getName(), "onCreate");
    }

    @Override
    public void onDestroy() {
        Log.e(this.getClass().getName(), "onDestroy");
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(this.getClass().getName(), "onBind");
        return new MyIBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(this.getClass().getName(), "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(this.getClass().getName(), "onStartCommand");
        startForeground(123, MyNotificationBuilder.generate(ContextCache.getContext(), "aaa"));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }
}

class MyIBinder extends Binder {
    public void sayHello() {
        Toast.makeText(ContextCache.getContext(), "hello", Toast.LENGTH_SHORT).show();
    }
}