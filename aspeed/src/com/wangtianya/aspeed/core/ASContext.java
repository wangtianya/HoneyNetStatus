package com.wangtianya.aspeed.core;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.RequestQueue;
import com.wangtianya.yaa.core.activity.YaaActivity;
import com.wangtianya.yaa.core.context.YaaContext;
import com.wangtianya.aspeed.bean.PingModel;
import com.wangtianya.aspeed.wigets.topbar.TopBarView;

/**
 * Created by tianya on 2015/8/31.
 */
public class ASContext extends YaaContext {

    public static class Caches{
        public static TopBarView topbar;
        public static List<PingModel> pingModels = new ArrayList<>();
        public static List<PingModel> pingModelCommands = new ArrayList<>();
        public static YaaActivity mainActivity;
    }



}
