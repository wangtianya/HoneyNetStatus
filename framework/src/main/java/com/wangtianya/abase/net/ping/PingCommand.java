
package com.wangtianya.abase.net.ping;

import java.util.Map;

import com.wangtianya.abase.net.ping.model.PingListener;

/**
 * Created by tianya on 15/4/22.
 */
public interface PingCommand {

    void ping(String host, int times, PingListener listener);

    void ping(String host, Map<String, String> param, PingListener listener);

    void cancel(String host);

    void cancelAll();
}
