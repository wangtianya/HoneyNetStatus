
package com.qjuzi.tools.net.ping2.inteface;

public interface PingListener {

    void onStart(PingRow row);

    void onProgress(PingRow row);

    void onFinish(PingResult result);

}
