package cn.wangtianya.learn.C1_FourBasicComponents.service;

import com.wangtianya.learn.common.AppModel;
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

        addItem("startService", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppModel.getContext(), LearnService.class);
                AppModel.getContext().startService(intent);
            }
        });

        addItem("stopService", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppModel.getContext(), LearnService.class);
                AppModel.getContext().stopService(intent);
            }
        });


        addItem("bindService", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppModel.getContext(), LearnService.class);
                getActivity().bindService(intent,connection, Context.BIND_AUTO_CREATE);
            }
        });

        addItem("unBindService", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().unbindService(connection);
            }
        });


        addItem("-------------------------", null);


        addItem("startIntentService", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppModel.getContext(), MyIntentService.class);
                intent.setAction("love");
                intent.putExtra("love", "mememe");
                AppModel.getContext().startService(intent);
            }
        });

        addItem("bindService", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppModel.getContext(), MyIntentService.class);
                getActivity().bindService(intent,connection, Context.BIND_AUTO_CREATE);
            }
        });

        addItem("stopIntentService", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppModel.getContext(), MyIntentService.class);
                AppModel.getContext().stopService(intent);
            }
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


