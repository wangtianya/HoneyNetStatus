/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.wangtianya.aspeed.activity;

import com.wangtianya.abase.core.activity.ABaseActivity;
import com.wangtianya.aspeed.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class WelcomeActivity extends ABaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        startTime();
    }

    private void startTime() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplication(), DrawerMainActivity.class));
                WelcomeActivity.this.finish();
            }
        }, 500);
    }
}
