
package com.wangtianya.yaa.core.context;

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

    public static void sendRequest(Request request) {
        sApplication.sendRequest(request);
    }

    public static void sendRequest(Request request, Object tag) {
        sApplication.sendRequest(request, tag);
    }

    public static void cancelRequest(Object tag) {
        sApplication.cancelRequest(tag);
    }
}
