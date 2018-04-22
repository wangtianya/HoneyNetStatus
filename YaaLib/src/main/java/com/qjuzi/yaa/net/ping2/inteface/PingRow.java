package com.qjuzi.yaa.net.ping2.inteface;

/**
 * Created by wangtianya on 2018/4/14.
 */

public class PingRow {
    public String ip;
    public int icmpSeq;
    public int ttl;
    public float time;

    public boolean isTimeout = false;

    public String consoleMsg;
}
