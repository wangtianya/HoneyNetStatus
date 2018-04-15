
package com.wangtianya.yaa.net.provider.isp.impl.taobao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.wangtianya.yaa.net.provider.ip.tool.GetIP;
import com.wangtianya.yaa.net.provider.isp.ISPAsyncListener;
import com.wangtianya.yaa.net.provider.isp.ISPModel;
import com.wangtianya.yaa.net.provider.isp.ISPProviderI;

/**
 * Created by tianya on 2015/8/31.
 */
public class TaobaoISPImpl implements ISPProviderI {

    public static final String mUrl = "http://ip.taobao.com/service/getIpInfo.php?ip=";

    @Override
    public ISPModel getIspModel(String host) {
        return convert(request(host));
    }

    @Override
    public void getIspModelAsync(final String host, final ISPAsyncListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listener.call(convert(request(GetIP.byDomain(host))));
            }
        });
    }

    public static String request(String host) {
        StringBuilder sbuilder = new StringBuilder();
        BufferedReader reader = null;
        try {
            URL url = new URL(mUrl + GetIP.byDomain(host));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();


            connection.addRequestProperty("Host", "ip.taobao.com");
            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) "
                    + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
            connection.connect();

            reader = new BufferedReader(new InputStreamReader((InputStream) connection.getContent()));

            String str;
            while ((str = reader.readLine()) != null) {
                sbuilder.append(str);
            }

        } catch (Exception e) {
            // pass
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                // pass
            }
        }
        return sbuilder.toString();
    }

    private ISPModel convert(String jsonStr) {
        ISPModel ispModel = null;
        try {
            ispModel = new ISPModel();
            JSONObject result = new JSONObject(jsonStr);
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

        return ispModel;
    }

}
