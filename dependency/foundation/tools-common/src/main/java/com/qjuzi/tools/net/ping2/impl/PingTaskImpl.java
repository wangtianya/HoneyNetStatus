package com.qjuzi.tools.net.ping2.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.qjuzi.tools.net.ping2.inteface.PingListener;
import com.qjuzi.tools.net.ping2.inteface.PingResult;
import com.qjuzi.tools.net.ping2.inteface.PingRow;
import com.qjuzi.tools.net.ping2.inteface.PingTask;

public class PingTaskImpl implements PingTask, Runnable {

    private static final Executor ThreadPool = Executors.newCachedThreadPool();

    private boolean isDead;

    private String host;

    private HashMap<String, String> args = new HashMap<>();

    private int times = Integer.MAX_VALUE;

    private PingListener listener;

    private Process process = null;

    public PingTaskImpl() {
        args.put("-W", "1");
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public void setTimes(int times) {
        this.times = times;
        args.put("-c", String.valueOf(times));
    }

    @Override
    public void setListener(PingListener listener) {
        this.listener = listener;
    }

    @Override
    public void start() {
        ThreadPool.execute(this);
    }

    @Override
    public void stop() {
        isDead = true;
        if (process != null) {
            process.destroy();
        }
    }

    @Override
    public void run() {
        PingResult result = new PingResult(host);

        try {
            String command = PingTaskImplHelper.getPingStr(host, args);
            process = Runtime.getRuntime().exec(command);
            BufferedReader buf = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String str;

            //读出所有信息并显示
            while ((str = buf.readLine()) != null) {
                if (Thread.interrupted() || isDead) {
                    break;
                }

                PingRow row = new PingRow();
                row.consoleMsg = str;

                if (!PingTaskImplHelper.isCanReachable(str)) {
                    break;
                }

                if (PingTaskImplHelper.isStartStr(str)) {
                    listener.onStart(row);
                }

                if (PingTaskImplHelper.isTimeoutStr(str)) {
                    row.isTimeout = true;
                    listener.onProgress(row);
                }

                if (PingTaskImplHelper.isRowStr(str)) {
                    PingTaskImplHelper.getRow(row, str);
                    listener.onProgress(row);
                }

                result.rows.add(row);

                if (PingTaskImplHelper.isStatisticsStr(str)) {
                    PingTaskImplHelper.getResult(result, str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        listener.onFinish(result);
    }

}
