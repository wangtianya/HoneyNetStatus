/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package com.wangtianya.aspeed.core;

import com.android.volley.toolbox.Volley;
import com.wangtianya.abase.core.context.ABaseApplication;
import com.wangtianya.abase.core.context.ABaseContext;

/**
 * Created by tianya on 2015/8/31.
 */
public class ASApplication extends ABaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ASContext.init(this);
        ASContext.Component.requestQueue = Volley.newRequestQueue(ABaseContext.getContext());;

    }

    @Override
    public void exit() {
        super.exit();
    }
}
