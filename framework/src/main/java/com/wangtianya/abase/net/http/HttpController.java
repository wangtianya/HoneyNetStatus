
package com.wangtianya.abase.net.http;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import com.wangtianya.abase.net.utils.HttpParamUtil;

import android.util.Log;

public class HttpController {

    private static final HttpClient mHttpClient = initHttpClient();

    public HttpController() {
    }

    public static ABaseResponse get(ABaseRequest lightRequest) throws Exception {
        HttpGet httpGet =
                new HttpGet(HttpParamUtil.buildGetUrlWithParams(lightRequest.getUrl(), lightRequest.getFormParams()));

        HttpResponse httpResponse = mHttpClient.execute(httpGet);

        ABaseResponse lightResponse = new ABaseResponse();
        if ((200 == httpResponse.getStatusLine().getStatusCode() || 304 == httpResponse.getStatusLine()
                                                                                   .getStatusCode())) {
            lightResponse.setSuccess(true);
            try {
                lightResponse.setTextContent(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        lightResponse.setResponse(httpResponse);
        return lightResponse;
    }

    public static ABaseResponse post(ABaseRequest lightRequest) throws Exception {
        HttpPost httpPost = new HttpPost(lightRequest.getUrl());
        httpPost.setEntity(new UrlEncodedFormEntity(lightRequest.getFormParams()));

        HttpResponse httpResponse = mHttpClient.execute(httpPost);

        ABaseResponse lightResponse = new ABaseResponse();
        if ((200 == httpResponse.getStatusLine().getStatusCode() || 304 == httpResponse.getStatusLine()
                                                                                   .getStatusCode())) {
            lightResponse.setSuccess(true);
            lightResponse.setTextContent(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
        }

        lightResponse.setResponse(httpResponse);
        return lightResponse;
    }

    public static void addCookie(String name, String value, String domain) {
        CookieStore cookieStore = ((AbstractHttpClient) mHttpClient).getCookieStore();
        BasicClientCookie basicClientCookie = new BasicClientCookie(name, value);
        basicClientCookie.setVersion(0);
        basicClientCookie.setDomain("." + domain);
        basicClientCookie.setPath("/");
        cookieStore.addCookie(basicClientCookie);
        ((AbstractHttpClient) mHttpClient).setCookieStore(cookieStore);
    }

    public static String getCookie(String domain, String key) {
        String cookieStr = key + "=";

        List<Cookie> cookies = ((AbstractHttpClient) mHttpClient).getCookieStore().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getDomain().contains(domain) && key.equals(cookie.getName())) {
                return cookieStr += cookie.getValue();
            }
        }
        return null;
    }

    private static HttpClient initHttpClient() {
        Log.v("HttpConnectionManager", "->> httpClient is null ->> do getHttpClient");

        // 设置组件参数, HTTP协议的版本,1.1/1.0/0.9
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
        HttpProtocolParams.setUseExpectContinue(params, true);

        // 设置连接超时时间
        int REQUEST_TIMEOUT = 10 * 1000; // 设置请求超时10秒钟
        int SO_TIMEOUT = 10 * 1000; // 设置等待数据超时时间10秒钟
        HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
        ConnManagerParams.setTimeout(params, 1000); // 从连接池中取连接的超时时间

        // 设置访问协议
        SchemeRegistry schreg = new SchemeRegistry();
        schreg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schreg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

        // 使用线程安全的连接管理来创建HttpClient
        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schreg);
        HttpClient httpClient = new DefaultHttpClient(conMgr, params);
        httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);// 是否带上session进入下一次请求
        return httpClient;
    }

    public HttpClient getHttpClient() {
        return mHttpClient;
    }

}
