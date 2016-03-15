
package com.wangtianya.abase.net.ping.model;

/**
 * Created by tianya on 15/4/22.
 */
public class PingResultRow {
    public String ip;
    public int icmp_seq;
    public int ttl;
    public float time;
    public boolean isTimeout = false;

    @Override
    public String toString() {
        return "ip: " + ip + ", icmp_seq" + icmp_seq + ", ttl" + ttl + ", time" + time + ", isTimeout" + isTimeout;
    }
}
