package com.qjuzi.yaa.net.traffic;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.net.TrafficStats;
import android.util.Log;

/**
 * Created by wangtianya on 2018/4/15.
 */

public class CurrentTrafficStats implements Runnable {

    public static CurrentTrafficStats getInstance() {
        return new CurrentTrafficStats();
    }

    private ArrayList<CurrentTrafficListener> listeners = new ArrayList<>();

    private long lastTime;

    private double lastSecondUpSize;
    private double lastSecondDownSize;

    private double currentUpSpeed;
    private double currentDownSpeed;

    private boolean isStop;

    public void addCurrentTrafficListener(CurrentTrafficListener currentTrafficListener) {
        listeners.add(currentTrafficListener);
    }

    public void stop() {
        isStop = true;
    }

    @Override
    public void run() {
        for (; ; ) {
            long currentTime = System.currentTimeMillis();
            long totalUpSize = TrafficStats.getTotalTxBytes();
            long totalDownSize = TrafficStats.getTotalRxBytes();

            Double timeCostRate = ((Long) (currentTime - lastTime)).doubleValue() / 1000F;

            Log.e("timeCostRate" + timeCostRate, "t: " + (currentTime - lastTime));

            if (lastSecondUpSize != 0 && lastSecondDownSize != 0) {
                currentUpSpeed = (totalUpSize - lastSecondUpSize) / timeCostRate;
                currentDownSpeed = (totalDownSize - lastSecondDownSize) / timeCostRate;

                Log.e("currentUpSpeed" + currentUpSpeed, "currentDownSpeed " + currentDownSpeed);
            }

            if (lastSecondUpSize != 0 && lastSecondDownSize != 0 && lastTime != 0) {
                for (CurrentTrafficListener listener : listeners) {
                    if (!isStop) {
                        listener.call(getSpeedStr(currentUpSpeed), getSpeedStr(currentDownSpeed));
                    } else {
                        return;
                    }
                }
            }

            lastSecondUpSize = totalUpSize;
            lastSecondDownSize = totalDownSize;
            lastTime = currentTime;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // pass
            }
        }
    }

    private String getSpeedStr(double speed) {
        double byteVal = speed;
        double KB = byteVal / 1024F;
        double MB = KB / 1024F;

        DecimalFormat df = new DecimalFormat("#.00");
        if (MB > 1) {
            return clearZeroEnd(df.format(MB)) + "MB/s";
        }

        if (KB > 1) {
            return clearZeroEnd(df.format(KB)) + "KB/s";
        }

        if (byteVal > 10) {
            return clearZeroEnd(df.format(byteVal)) + "B/s";
        }

        return "0B/s";
    }

    private String clearZeroEnd(String number) {
        if (number.contains(".") && number.endsWith("00")) {
            return number.substring(0, number.length() - 3);
        } else if (number.contains(".") && number.endsWith("0")) {
            return number.substring(0, number.length() - 1);
        }
        return number;
    }
}
