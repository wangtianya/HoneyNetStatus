
package com.qjuzi.yaa.core.context;

import com.android.volley.Request;

/**
 * 请保证此类被尽早执行
 */
public class YaaContext {

    private static YaaApplication sApplication = null;

    public static void init(YaaApplication application) {
        sApplication = application;
    }

    public static YaaApplication getContext() {
        return sApplication;
    }
}
