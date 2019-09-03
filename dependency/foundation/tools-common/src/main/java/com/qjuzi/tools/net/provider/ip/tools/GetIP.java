package com.qjuzi.tools.net.provider.ip.tools;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by wangtianya on 2018/4/15.
 */

public class GetIP {

    public static void main(String[] args) {
        System.out.println(byDomain("www.baidu.com"));
    }

    public static String byDomain(String domain) {
        InetAddress ip;
        try {
            ip = InetAddress.getByName(domain.trim());
            return ip.getHostAddress();
        } catch (UnknownHostException e) {
            // pass
        }
        return null;
    }

    public static String[] listByDomain(String domain) {
        String[] ipsStrs = {};
        try {
            InetAddress[] ips = InetAddress.getAllByName(domain);
            ipsStrs = new String[ips.length];

            for (int i = 0; i < ips.length; i++) {
                ipsStrs[i] = ips[i].getHostAddress();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ipsStrs;

    }

    public static String getIntranetIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getIpAddressFromWeb() {
        try {
            Document document = Jsoup.connect("http://2017.ip138.com/ic.asp").get();
            return extractIp(document.html());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String extractIp(String res) {
        String regEx = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
        Matcher m = Pattern.compile(regEx).matcher(res);
        while (m.find()) {
            return m.group();
        }
        return null;
    }
}
