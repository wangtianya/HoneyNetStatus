
/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.qjuzi.architecure.tree.context;

import android.app.Application;

public class Tree {

    private static Application application = null;

    public static void init(Application application) {
        Tree.application = application;
    }

    public static Application getContext() {
        return application;
    }
}
