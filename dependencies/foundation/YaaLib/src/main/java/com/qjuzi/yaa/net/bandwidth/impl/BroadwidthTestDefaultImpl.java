
package com.qjuzi.yaa.net.bandwidth.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.qjuzi.yaa.core.util.YaaLog;
import com.qjuzi.yaa.net.bandwidth.BroadWidthListener;
import com.qjuzi.yaa.net.bandwidth.BroadwidthTestI;
import com.qjuzi.yaa.other.RandomUtil;

import android.net.TrafficStats;

/**
 * 实现类，写的有点早，所以有点丑
 */
public class BroadwidthTestDefaultImpl implements BroadwidthTestI {
    YaaLog log = YaaLog.getLogger();

    @Override
    public void start(String[] urls, int seconds, BroadWidthListener listener) {
        initBroadWidthTester(mUrls, listener, seconds);
        this.start();
    }

    public static enum Status {
        Ready, Running, Over
    }

    private String[] mUrls = {
            "http://dlsw.baidu.com/sw-search-sp/soft/a8/27390/androidstudio.1401270841.exe",
            "http://42.236.2.151/dlied1.qq.com/lol/full/LOL_V3.1.5.0_FULL.7z.001?mkey=55544e314ea9a1c8&f=178a&p=.001",
            "http://down.360safe.com/setup.exe"
    };

    private BroadWidthListener mListener = null;

    int mSeconds = 0;
    int mThreadsNum = 8;
    Status mStatus = Status.Ready;

    public BroadwidthTestDefaultImpl() {
    }

    public void initBroadWidthTester(String[] urls, BroadWidthListener listener, int seconds) {
        this.mUrls = urls;
        mSeconds = seconds;
        mListener = listener;
    }

    public void start() {
        Executor executor = Executors.newCachedThreadPool();
        for (int i = 0; i < mThreadsNum; i++) {
            executor.execute(new ShaoliuliangDownRunnable(mUrls[i % mUrls.length]));
            // executor.execute(new ShaoliuliangUpRunnable());
        }
        executor.execute(new ReadSizeFromSystemRunnable());
        mListener.onStart();

        mStatus = Status.Running;
    }

    public void stop() {
        mStatus = Status.Over;
        mListener = null;
    }

    class ShaoliuliangDownRunnable implements Runnable {

        private String urlStr;

        public ShaoliuliangDownRunnable(String url) {
            this.urlStr = url;
        }

        @Override
        public void run() {
            InputStream in;
            try {
                int connectTimeout = 30 * 1000; // 连接超时:30s
                int readTimeout = 1 * 1000 * 1000; // IO超时:1min
                URL url = new URL(urlStr);
                URLConnection conn = url.openConnection();
                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
                conn.connect();
                in = conn.getInputStream();

                byte[] buffer = new byte[1024];
                int readSize;
                while ((readSize = in.read(buffer)) > 0) {
                    if (mStatus == Status.Over) {
                        break;
                    }
                }
                in.close();
            } catch (Exception e) {
                log.e("Exception :  " + this.urlStr + e.getMessage());
            }

        }
    }

    class ShaoliuliangUpRunnable implements Runnable {
        String domain = "www.baidu.com";

        @Override
        public void run() {
            try {
                sendUDP(domain, 10000);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void sendUDP(String domain, int remotePort) throws IOException {
            DatagramSocket datagramSocket;
            DatagramSocket ds = null;
            try {
                datagramSocket = new DatagramSocket(RandomUtil.getNumber(5000, 10000));
                ds = datagramSocket;  //实例化套间字，指定自己的port
                if (ds == null) {
                    log.d("上传现成失败");
                    return;
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }

            byte[] buf = new byte[2048];  //数据
            InetAddress destination = null;
            try {
                destination = InetAddress.getByName(domain);  //需要发送的地址
            } catch (UnknownHostException e) {
                System.out.println("Cannot open findhost!");
            }

            try {
                while (true) {
                    if (mStatus == Status.Over) {
                        break;
                    }
                    ds.send(new DatagramPacket(buf, buf.length, destination, remotePort));  //发送数据
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ds.close();
            ds.disconnect();
        }
    }

    class ReadSizeFromSystemRunnable implements Runnable {
        private long maxDownSize = 0;
        private long totalDownSize = 0;
        private long lastDownSize = 0;

        private long maxUpSize = 0;
        private long totalUpSize = 0;
        private long lastUpSize = 0;

        public long getSizeDownFromSystem() {
            long nowSystemSize = TrafficStats.getTotalRxBytes();
            if (lastDownSize == 0) {
                lastDownSize = nowSystemSize;
            }
            long willReturn = (nowSystemSize - lastDownSize) / 1024;
            lastDownSize = nowSystemSize;
            if (maxDownSize < willReturn) {
                maxDownSize = willReturn;
            }
            totalDownSize += willReturn;
            return willReturn;
        }

        public long getSizeUpFromSystem() {
            long nowSystemSize = TrafficStats.getTotalTxBytes();
            if (lastUpSize == 0) {
                lastUpSize = nowSystemSize;
            }
            long willReturn = (nowSystemSize - lastUpSize) / 1024;
            lastUpSize = nowSystemSize;
            if (maxUpSize < willReturn) {
                maxUpSize = willReturn;
            }
            totalUpSize += willReturn;
            return willReturn;
        }

        @Override
        public void run() {
            int useTime = 0;
            try {
                mSeconds++;
                // Thread.sleep(1000);
                while (mSeconds > 0) {
                    Thread.sleep(1000);
                    if (mStatus == Status.Over) {
                        break;
                    }
                    long kbPerSDown = getSizeDownFromSystem();
                    long kbPerSUp = getSizeUpFromSystem();
                    mSeconds--;
                    useTime++;
                    int progress = (int) ((useTime * 100f) / ((mSeconds - 1 + useTime) * 100f) * 100);
                    mListener.onProgress(kbPerSDown, kbPerSUp, progress, mSeconds);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mStatus = Status.Over;
            if (mListener == null) {
                return;
            }
            if (useTime == 0) {
                mListener.onFinish(0, 0, 0);
                return;
            }
            useTime--;
            useTime = useTime == 0 ? useTime + 1 : useTime;
            mListener.onFinish(maxDownSize, totalDownSize / useTime, useTime);
        }
    }

}
