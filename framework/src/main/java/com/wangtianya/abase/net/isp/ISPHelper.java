
package com.wangtianya.abase.net.isp;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class ISPHelper {

    public static final String url = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    public static ISPModel fetchFromTaobao() {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            String IPSJsonStr =
                    EntityUtils.toString(httpClient.execute(new HttpGet(ISPHelper.url + url)).getEntity());
            JSONObject result = new JSONObject(IPSJsonStr);
            if ("0".equals(result.getString("code"))) {
                ISPModel ispModel = new ISPModel();
                ispModel.country = result.optJSONObject("data").optString("country");
                ispModel.area = result.optJSONObject("data").optString("area");
                ispModel.region = result.optJSONObject("data").optString("region");
                ispModel.city = result.optJSONObject("data").optString("city");
                ispModel.county = result.optJSONObject("data").optString("county");
                ispModel.isp = result.optJSONObject("data").optString("isp");
                ispModel.ip = result.optJSONObject("data").optString("ip");
                return ispModel;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
