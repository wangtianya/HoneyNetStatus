package com.qjuzi.yaa.net.ping2;

import com.qjuzi.yaa.net.ping2.impl.PingTaskImpl;
import com.qjuzi.yaa.net.ping2.inteface.PingListener;
import com.qjuzi.yaa.net.ping2.inteface.PingTask;

/**
 * todo: 1. 如果有一天和人协作，再换成抽象工厂类。自己用，不要过度去整了。
 * todo: 2. 时间检查，纪录最后一次活动时间，超时就时阻塞或异常了
 */
public class PingTaskFactory {

    public static PingTask newOne(String host, PingListener listener) {
        PingTask pingTask = new PingTaskImpl();
        pingTask.setHost(host);
        pingTask.setListener(listener);
        return pingTask;
    }

    public static PingTask newOne(String host, int times, PingListener listener) {
        PingTask pingTask = new PingTaskImpl();
        pingTask.setHost(host);
        pingTask.setTimes(times);
        pingTask.setListener(listener);
        return pingTask;
    }
}
