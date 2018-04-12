
package com.wangtianya.aspeed.activity;

import com.wangtianya.yaa.core.activity.YaaActivity;
import com.wangtianya.aspeed.R;
import com.wangtianya.aspeed.bean.PingModel;
import com.wangtianya.aspeed.core.ASConfig;
import com.wangtianya.aspeed.core.ASContext;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class WelcomeActivity extends YaaActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initFirst();

        initEvertime();

        startTime();
    }



    private void startTime() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplication(), MainActivity.class));
                WelcomeActivity.this.finish();
            }
        }, 500);
    }

    private void initFirst() {
        if (ASConfig.Common.isDeBug || ASConfig.Pref.isFirstComeIn()) {
            ASContext.Caches.pingModels.clear();
            PingModel.firstInit();

            ASConfig.Pref.disableFirstComeIn();
        }
    }

    private void initEvertime() {
        PingModel.getPingItems();
        PingModel.getCommandFromInternet();
    }
}
