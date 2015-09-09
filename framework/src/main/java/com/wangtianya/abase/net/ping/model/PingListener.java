
package com.wangtianya.abase.net.ping.model;

public interface PingListener {

    void onStart();

    void onProgress(PingResultRow row);

    void onFinish(PingResult result);

}
