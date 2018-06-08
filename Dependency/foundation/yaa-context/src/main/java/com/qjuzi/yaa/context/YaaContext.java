
package com.qjuzi.yaa.context;


public class YaaContext {

    private static YaaApplication application = null;

    public static void init(YaaApplication application) {
        YaaContext.application = application;
    }

    public static YaaApplication getContext() {
        return application;
    }
}
