
package com.qjuzi.yaa.net.ping2.inteface;


public interface PingListener {

    void onStart(PingRow row);

    void onProgress(PingRow row);

    void onFinish(PingResult result);

}
