package com.qjuzi.yaa.net.bandwidth;

import java.util.ArrayList;
import java.util.List;

import com.qjuzi.yaa.net.bandwidth.impl.BroadwidthTestDefaultImpl;

public class BroadwidthTest {
    private static Class mClass = BroadwidthTestDefaultImpl.class;

    private static List<BroadwidthTestI> BroadwidthTests = new ArrayList<BroadwidthTestI>();

    public static BroadwidthTestI start(String[] urls, int seconds, BroadWidthListener listener) {
        BroadwidthTestI instance = null;
        try {
            stopAll();
            instance = (BroadwidthTestI) mClass.getConstructor().newInstance();
            BroadwidthTests.add(instance);
            instance.start(urls, seconds, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static void stopAll() {
        for (BroadwidthTestI bw: BroadwidthTests) {
            bw.stop();
        }
        BroadwidthTests.clear();
    }
}
