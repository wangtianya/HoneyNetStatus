
package com.wangtianya.yaa.net.ping;

import java.util.Map;

import com.wangtianya.yaa.net.ping.model.PingListener;

public class PingManager implements PingCommand {

    private static PingCommand mPingCommand;

    public static PingManager INCETANCE = new PingManager();

    public static synchronized PingManager getInstance() {
        if (mPingCommand != null) {
            return INCETANCE;
        }
        mPingCommand = new PingManagerForLinux();
        return INCETANCE;
    }

    @Override
    public void ping(String host, int times, PingListener listener) {
        mPingCommand.ping(host, times, listener);
    }

    @Override
    public void ping(final String host, final Map<String, String> param, final PingListener listener) {
        mPingCommand.ping(host, param, listener);
    }

    @Override
    public void cancel(String host) {
        mPingCommand.cancel(host);
    }

    @Override
    public void cancelAll() {
        mPingCommand.cancelAll();
    }
}
