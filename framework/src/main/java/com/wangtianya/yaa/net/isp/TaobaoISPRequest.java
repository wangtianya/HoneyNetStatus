
package com.wangtianya.yaa.net.isp;

import java.io.UnsupportedEncodingException;

import org.json.JSONObject;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * Created by tianya on 2015/8/31.
 */
public class TaobaoISPRequest extends Request <ISPModel>{

    public static final String mUrl = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    private final Response.Listener<ISPModel> mListener;

    public TaobaoISPRequest(int method, String ip, Response.Listener<ISPModel> listener, Response.ErrorListener
            errorListener) {
        super(method, mUrl + ip, errorListener);
        this.mListener = listener;
    }

    public TaobaoISPRequest(String ip, Response.Listener<ISPModel> listener, Response.ErrorListener errorListener) {
        this(Method.GET, ip, listener, errorListener);
    }
    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        ISPModel ispModel = null;
        String parsed;

        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException var4) {
            parsed = new String(response.data);
        }


        try {
            ispModel = new ISPModel();
            JSONObject result = new JSONObject(parsed);
            if ("0".equals(result.getString("code"))) {
                ispModel.country = result.optJSONObject("data").optString("country");
                ispModel.area = result.optJSONObject("data").optString("area");
                ispModel.region = result.optJSONObject("data").optString("region");
                ispModel.city = result.optJSONObject("data").optString("city");
                ispModel.county = result.optJSONObject("data").optString("county");
                ispModel.isp = result.optJSONObject("data").optString("isp");
                ispModel.ip = result.optJSONObject("data").optString("ip");
            } else {
                ispModel = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ispModel == null) {
            return Response.error(new VolleyError("解析互出错！"));
        } else {
            return Response.success(ispModel, HttpHeaderParser.parseCacheHeaders(response));
        }
    }

    @Override
    protected void deliverResponse(ISPModel o) {
        mListener.onResponse(o);
    }

}
