package com.wangtianya.aspeed.core;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.RequestQueue;
import com.wangtianya.abase.core.activity.ABaseActivity;
import com.wangtianya.abase.core.context.ABaseContext;
import com.wangtianya.aspeed.bean.PingModel;
import com.wangtianya.aspeed.wigets.topbar.TopBarView;

/**
 * Created by tianya on 2015/8/31.
 */
public class ASContext extends ABaseContext {

    public static class Component {
        public static RequestQueue requestQueue; // application init
    }

    public static RequestQueue getRequestQueue() {
        return Component.requestQueue;
    }

    public static class Caches{
        public static TopBarView topbar;
        public static List<PingModel> pingModels = new ArrayList<>();
        public static List<PingModel> pingModelCommands = new ArrayList<>();
        public static ABaseActivity mainActivity;
    }



}
