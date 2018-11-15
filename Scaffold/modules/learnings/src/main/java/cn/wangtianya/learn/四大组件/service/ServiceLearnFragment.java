package cn.wangtianya.learn.四大组件.service;

import com.qjuzi.architecure.base.context.ContextCache;
import com.wangtianya.learn.common.ItemFragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

/**
 * Created by wangtianya on 2018/3/14.
 */

public class ServiceLearnFragment extends ItemFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        addClickItem("startService", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContextCache.getContext(), LearnService.class);
                ContextCache.getContext().startService(intent);
            }
        });

        addClickItem("stopService", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContextCache.getContext(), LearnService.class);
                ContextCache.getContext().stopService(intent);
            }
        });


        addClickItem("bindService", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContextCache.getContext(), LearnService.class);
                getActivity().bindService(intent,connection, Context.BIND_AUTO_CREATE);
            }
        });

        addClickItem("unBindService", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().unbindService(connection);
            }
        });


        addClickItem("-------------------------", null);


        addClickItem("startIntentService", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContextCache.getContext(), MyIntentService.class);
                intent.setAction("love");
                intent.putExtra("love", "mememe");
                ContextCache.getContext().startService(intent);
            }
        });

        addClickItem("bindService", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContextCache.getContext(), MyIntentService.class);
                getActivity().bindService(intent,connection, Context.BIND_AUTO_CREATE);
            }
        });

        addClickItem("stopIntentService", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContextCache.getContext(), MyIntentService.class);
                ContextCache.getContext().stopService(intent);
            }
        });



        addClickItem("startJobIntentTestService", v ->{
            Intent intent = new Intent(ContextCache.getContext(), JobIntentTestService.class);
//            ContextCache.getContext().startService(intent);
            JobIntentTestService.enqueueWork(ContextCache.getContext(), intent);
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("onServiceConnected", "onServiceConnected： " + name);
            ((MyIBinder)service).sayHello();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("onServiceConnected", "onServiceConnected");
        }
    };

}


