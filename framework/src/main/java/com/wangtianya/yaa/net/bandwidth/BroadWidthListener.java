
package com.wangtianya.yaa.net.bandwidth;

public interface BroadWidthListener {

    void onStart();

    void onProgress(long kbSecondDown, long kbSecondUp, int progress, int remainTime);

    void onFinish(long max, long average, long useTime);

}
