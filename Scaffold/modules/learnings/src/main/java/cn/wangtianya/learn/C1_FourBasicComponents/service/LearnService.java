package cn.wangtianya.learn.C1_FourBasicComponents.service;

import com.qjuzi.architecure.base.context.ContextCache;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

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
        return super.onStartCommand(intent, flags, startId);
    }
}

class MyIBinder extends Binder {
    public void sayHello() {
        Toast.makeText(ContextCache.getContext(), "hello", Toast.LENGTH_SHORT).show();
    }
}