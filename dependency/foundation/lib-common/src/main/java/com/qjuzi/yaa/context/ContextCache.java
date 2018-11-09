
package com.qjuzi.yaa.context;

import android.app.Application;

public class ContextCache {

    private static Application application = null;

    public static void init(Application application) {
        ContextCache.application = application;
    }

    public static Application getContext() {
        return application;
    }
}
