
package com.wangtianya.yaa.net.ping;

import java.util.Map;

import com.wangtianya.yaa.net.ping.model.PingResult;
import com.wangtianya.yaa.net.ping.model.PingResultRow;

public class PingManagerForLinuxEM {

    public static PingResult getResult(String str, PingResult pr) {
        pr.packetsReceived = Integer.valueOf(str.substring(str.indexOf("tted,") + 6, str.indexOf(" received")));
        pr.packetsTransmitted = Integer.valueOf(str.substring(0, str.indexOf(" packets transmitted,")));
        if (pr.packetsReceived > 0) {
            pr.isSuccess = true;
        }
        return pr;
    }

    public static PingResultRow getRow(String str) {
        PingResultRow prr = new PingResultRow();
        prr.ip = str.substring(str.indexOf("from") + 5, str.indexOf(": icmp_seq"));
        prr.icmp_seq = Integer.valueOf(str.substring(str.indexOf("icmp_seq=") + 9, str.indexOf(" ttl=")));
        prr.ttl = Integer.valueOf(str.substring(str.indexOf("ttl=") + 4, str.indexOf(" time=")));
        prr.time = Float.valueOf(str.substring(str.indexOf("time=") + 5, str.indexOf(" ms")));
        if (prr.time > 0){
            prr.isTimeout = false;
        }
        return prr;
    }

    public static  boolean isCanReachable(String str) {
        boolean isCanReachable = true;

        if (str.indexOf("Unknown host") > 0){
            isCanReachable  = false;
        }

        if (str.indexOf("Net Unreachable") > 0){
            isCanReachable  = false;
        }
        return isCanReachable;
    }

    public static  String getPingStr(String host, Map<String, String> param) {
        String execStr = "ping";
        for (String key : param.keySet()) {
            execStr += " " + key;
            if (param.get(key) != null) {
                execStr += " " + param.get(key);
            }
        }
        execStr += " " + host;

        return execStr;
    }

    public static  boolean isStartStr(String str) {
        return str.indexOf("ING") > 0;
    }

    public static  boolean isTimeoutStr(String str) {
        return str.indexOf("timeout") > 0;
    }

    public static  boolean isRowStr(String str) {
        return str.indexOf("bytes") > 0 && str.indexOf("from") > 0 && str.indexOf(": icmp_seq") > 0 && str.indexOf("ttl") > 0 && str.indexOf("time") > 0;
    }

    public static  boolean isStatisticsStr(String str) {
        return str.indexOf("transmitted") > 0 && str.indexOf("received") > 0 && str.indexOf("loss") > 0;
    }
}
