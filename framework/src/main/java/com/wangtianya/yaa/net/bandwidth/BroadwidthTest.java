package com.wangtianya.yaa.net.bandwidth;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.wangtianya.yaa.net.bandwidth.impl.BroadwidthTestDefaultImpl;

public class BroadwidthTest {
    private static Class mClass = BroadwidthTestDefaultImpl.class;

    private static List<BroadwidthTestI> BroadwidthTests = new ArrayList<BroadwidthTestI>();

    /**
     * 慎用，必须是BroadwidthTestI的实现类
     */
    public static void changImpl(Class<BroadwidthTestI> clazz) {
        mClass = clazz;
    }

    public static BroadwidthTestI start(String[] urls, int seconds, BroadWidthListener listener) {
        BroadwidthTestI instance = null;
        try {
            stopAll();
            instance = (BroadwidthTestI) mClass.getConstructor().newInstance();
            BroadwidthTests.add(instance);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        instance.start(urls, seconds, listener);
        return instance;
    }

    public static void stopAll() {
        for (BroadwidthTestI bw: BroadwidthTests) {
            bw.stop();
        }
        BroadwidthTests.clear();
    }
}
