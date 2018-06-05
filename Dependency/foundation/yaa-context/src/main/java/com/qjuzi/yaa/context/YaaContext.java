
package com.qjuzi.yaa.context;


public class YaaContext {

    private static YaaApplication application = null;

    public static void init(YaaApplication application) {
        application = application;
    }

    public static YaaApplication getContext() {
        return application;
    }
}
