
package com.wangtianya.abase.net.bandwidth;

public interface BroadwidthTestI {

    enum Status {
        Ready, Running, Over
    }

    void start(String[] urls, int seconds, BroadWidthListener listener);

    void stop();
}
