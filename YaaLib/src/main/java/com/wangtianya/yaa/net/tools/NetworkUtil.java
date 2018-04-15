
package com.wangtianya.yaa.net.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    /**
     * 网络主机 是否通达
     *
     * @param host
     * @param timeout
     *
     * @return
     */
//    public static boolean isReachable(String host, int timeout) {
//        boolean result = false;
//
//        InetAddress inetAddress = null;
//        try {
//            inetAddress = InetAddress.getByName(host);
//            result = inetAddress.isReachable(timeout);
//
//            if (result == true) {
//                return true;
//            } else {
//                result = isReachableByPing(host, timeout / 1000);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//    private static boolean isReachableByPing(String host, int timeout) {
//        if (pingForResult(host, 1, timeout).isSuccess) {
//            return true;
//        }
//        return false;
//    }

//    /**
//     * 获取 ping 结果
//     *
//     * @param host
//     * @param times
//     * @param timeout
//     *
//     * @return
//     */
//    public static PingResult pingForResult(String host, int times, int timeout) {
//        Process p;
//        PingResult pr = new PingResult(host);
//        try {
//            p = Runtime.getRuntime().exec("ping -c " + times + " -W " + timeout + " " + host);
//
//            BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String str = new String();
//
//            //读出所有信息并显示
//            while ((str = buf.readLine()) != null) {
//                if (PingManagerForLinuxEM.isStatisticsStr(str)) {
//                    return PingManagerForLinuxEM.getResult(str, pr);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return pr;
//    }




//    /**
//     * 网络是否是CONNECTED状态
//     */
//    public boolean isConnected(Activity activity) {
//        ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (manager == null) {
//            return false;
//        } else {
//            NetworkInfo[] info = manager.getAllNetworkInfo();
//            if (info != null) {
//                for (int i = 0; i < info.length; i++) {
//                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }

//    /**
//     * 网络是否连接或正在连接中
//     */
//    public boolean isConnectedOrConnecting(Activity activity) {
//        ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (manager != null) {
//            NetworkInfo network = manager.getActiveNetworkInfo();
//            if (network != null && network.isConnectedOrConnecting()) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static boolean isIpv4(String ipAddress) {

        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();

    }
}

