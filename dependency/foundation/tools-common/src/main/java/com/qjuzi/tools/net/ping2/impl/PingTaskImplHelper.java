
package com.qjuzi.tools.net.ping2.impl;

import java.util.Map;

import com.qjuzi.tools.net.ping2.inteface.PingResult;
import com.qjuzi.tools.net.ping2.inteface.PingRow;

public class PingTaskImplHelper {

    public static PingResult getResult(PingResult pr, String str) {
        pr.packetsReceived = Integer.valueOf(str.substring(str.indexOf("tted,") + 6, str.indexOf(" received")));
        pr.packetsTransmitted = Integer.valueOf(str.substring(0, str.indexOf(" packets transmitted,")));
        pr.consoleMsg = str;
        return pr;
    }

    public static PingRow getRow(PingRow row, String str) {
        row.ip = str.substring(str.indexOf("from") + 5, str.indexOf(": icmp_seq"));
        row.icmpSeq = Integer.valueOf(str.substring(str.indexOf("icmp_seq=") + 9, str.indexOf(" ttl=")));
        row.ttl = Integer.valueOf(str.substring(str.indexOf("ttl=") + 4, str.indexOf(" time=")));
        row.time = Float.valueOf(str.substring(str.indexOf("time=") + 5, str.indexOf(" ms")));
        return row;
    }

    public static boolean isCanReachable(String str) {
        boolean isCanReachable = true;

        if (str.indexOf("Unknown host") > 0) {
            isCanReachable = false;
        }

        if (str.indexOf("Net Unreachable") > 0) {
            isCanReachable = false;
        }
        return isCanReachable;
    }

    public static String getPingStr(String host, Map<String, String> param) {
        String execStr = "/system/bin/ping";
        for (String key : param.keySet()) {
            execStr += " " + key;
            if (param.get(key) != null) {
                execStr += " " + param.get(key);
            }
        }
        execStr += " " + host;

        return execStr;
    }

    public static boolean isStartStr(String str) {
        return str.indexOf("ING") > 0;
    }

    public static boolean isTimeoutStr(String str) {
        return str.indexOf("timeout") > 0;
    }

    public static boolean isRowStr(String str) {
        return str.indexOf("bytes") > 0 && str.indexOf("from") > 0 && str.indexOf(": icmp_seq") > 0
                && str.indexOf("ttl") > 0 && str.indexOf("time") > 0;
    }

    public static boolean isStatisticsStr(String str) {
        return str.indexOf("transmitted") > 0 && str.indexOf("received") > 0 && str.indexOf("loss") > 0;
    }
}
