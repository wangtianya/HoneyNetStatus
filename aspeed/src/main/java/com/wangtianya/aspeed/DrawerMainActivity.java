package com.wangtianya.aspeed;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wangtianya.abase.core.activity.ABaseActivity;
import com.wangtianya.abase.ioc.annotation.InjectView;
import com.wangtianya.abase.net.isp.ISPModel;
import com.wangtianya.abase.net.isp.TaobaoISPRequest;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by tianya on 2015/8/31.
 */
public class DrawerMainActivity extends ABaseActivity {

    @InjectView(id = R.id.tv_main)
    private TextView mTvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Response.Listener listener = new Response.Listener<ISPModel>() {
            @Override
            public void onResponse(ISPModel response) {
                mTvMain.setText(response.isp);
            }
        };
        addRequest(new TaobaoISPRequest("220.181.38.109", listener, null));
    }
}
