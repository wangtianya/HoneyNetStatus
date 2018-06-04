/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.wangtianya.learn.common;

import android.content.Context;

/**
 * Created by tianya on 2017/4/27.
 */

public class AppModel {
    private static Context context;



    public static void setContext(Context context) {
        AppModel.context = context;
    }
    public static Context getContext() {
        return context;
    }


}
