
package com.wangtianya.abase.net.utils;

import java.util.List;

import org.apache.http.NameValuePair;

public class HttpParamUtil {

    /**
     * Used Mark: HttpContorller
     */
    public static String buildGetUrlWithParams(String url, List<NameValuePair> forms) {
        boolean isFirst = true;
        StringBuffer urlStringBuffer = new StringBuffer();
        urlStringBuffer.append(url);
        urlStringBuffer.append("?");
        for (NameValuePair n : forms) {
            urlStringBuffer.append(n.getName());
            urlStringBuffer.append("=");
            urlStringBuffer.append(n.getValue());
            urlStringBuffer.append("&");
        }
        if (!isFirst) {
            return urlStringBuffer.substring(0, urlStringBuffer.length() - 1);
        }
        return urlStringBuffer.toString();
    }

    public static String buildPostUrlWithParams(List<NameValuePair> forms) {
        boolean isFirst = true;
        StringBuffer urlStringBuffer = new StringBuffer();
        for (NameValuePair n : forms) {
            urlStringBuffer.append(n.getName());
            urlStringBuffer.append("=");
            urlStringBuffer.append(n.getValue());
            urlStringBuffer.append("&");
        }
        if (!isFirst) {
            return urlStringBuffer.substring(0, urlStringBuffer.length() - 1);
        }
        return urlStringBuffer.toString();
    }
}
