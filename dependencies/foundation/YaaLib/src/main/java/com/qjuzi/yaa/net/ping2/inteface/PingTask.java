package com.qjuzi.yaa.net.ping2.inteface;

public interface PingTask {

    void setHost(String host);

    void setTimes(int times);

    void setListener(PingListener listener);

    void start();

    void stop();
}
