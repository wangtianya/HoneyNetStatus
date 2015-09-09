
package com.wangtianya.abase.core.context;

import android.app.Application;

/**
 * 请保证此类被尽早执行
 */
public class ABaseContext {

    private static Application sApplication = null;

    public static void init(Application application) {
        sApplication = application;
    }

    public static Application getContext() {
        return sApplication;
    }
}
