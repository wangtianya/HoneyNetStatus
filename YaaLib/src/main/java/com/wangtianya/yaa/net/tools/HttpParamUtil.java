
package com.wangtianya.yaa.net.tools;
import java.util.Map;

public class HttpParamUtil {

    public static String buildGetUrlWithParams(String url, Map<String, String> forms) {
        boolean isFirst = true;
        StringBuffer urlStringBuffer = new StringBuffer();
        urlStringBuffer.append(url);
        urlStringBuffer.append("?");
        urlStringBuffer.append(buildParams(forms));
        if ((urlStringBuffer.lastIndexOf("&") == urlStringBuffer.length())
                || (urlStringBuffer.lastIndexOf("?") == urlStringBuffer.length())) {
            return urlStringBuffer.substring(0, urlStringBuffer.length() - 1);
        }
        return urlStringBuffer.toString();
    }

    public static String buildParams(Map<String, String> forms) {
        StringBuffer urlStringBuffer = new StringBuffer();
        for (String key : forms.keySet()) {
            urlStringBuffer.append(key);
            urlStringBuffer.append("=");
            urlStringBuffer.append(forms.get(key));
            urlStringBuffer.append("&");
        }
        return urlStringBuffer.substring(0, urlStringBuffer.length() - 1);
    }
}
