
package com.wangtianya.abase.net.http;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import com.wangtianya.abase.core.context.ABaseLog;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

/**
 * @Description: Http任务
 * @Email:i@wangtianya.cn
 * @Date Apr 13, 2014 12:02:45 AM
 */
public class HttpTask extends AsyncTask<Integer, Integer, ABaseResponse> {

    private static final OnStartHandler onStartHandler = new OnStartHandler();

    private ABaseLog log = ABaseLog.getLogger();

    private ABaseRequest mABaseRequest;

    public HttpTask(ABaseRequest lightRequest) {
        super();
        this.mABaseRequest = lightRequest;
        onStartHandler.getLooper();
    }

    @Override
    protected void onPreExecute() {
        if (isCancelled()) {
            return;
        }
        onStartHandler.obtainMessage(OnStartHandler.ONSTART, mABaseRequest).sendToTarget();
    }

    @Override
    protected ABaseResponse doInBackground(Integer... params) {
        if (isCancelled()) {
            return null;
        }
        ABaseResponse lightResponse = this.request();
        return lightResponse;
    }

    @Override
    protected void onPostExecute(ABaseResponse lightResponse) {
        if (isCancelled()) {
            return;
        }

        mABaseRequest.getHttpListeners().remove(null);

        lightResponse.setRequest(mABaseRequest);

        for (HttpListener httpListener : mABaseRequest.getHttpListeners()) {
            httpListener.onCompleted(lightResponse, this);
        }

        if (lightResponse == null || !lightResponse.isSuccess()) {
            for (HttpListener httpListener : mABaseRequest.getHttpListeners()) {
                httpListener.onFailed(lightResponse);
            }
        } else {
            for (HttpListener httpListener : mABaseRequest.getHttpListeners()) {
                httpListener.onSuccess(lightResponse);
            }
        }
    }

    public boolean cancel() {
        return super.cancel(true);
    }

    public ABaseResponse request() {
        ABaseResponse lightResponse = new ABaseResponse();
        int method = this.mABaseRequest.getHttpMthod();
        try {
            switch (method) {
                case ABaseRequest.HttpMethod.GET:
                    return HttpController.get(mABaseRequest);
                case ABaseRequest.HttpMethod.POST:
                    return HttpController.post(mABaseRequest);
            }
        } catch (UnknownHostException e) {// 网络未连接或找不到主机
            log.e(e.getMessage());
        } catch (SocketTimeoutException e) {// socket链接超时，网络链接不稳定或被墙了
            log.e(e.getMessage());
        } catch (Exception e) {
            log.e(e.getMessage());
        }
        return lightResponse;
    }

    private static class OnStartHandler extends Handler {
        public static final int ONSTART = 0;

        @Override
        public void handleMessage(Message msg) {
            ABaseRequest lightRequest = (ABaseRequest) msg.obj;
            switch (msg.what) {
                case ONSTART:
                    for (HttpListener httpListener : lightRequest.getHttpListeners()) {
                        httpListener.onstart(lightRequest);
                    }
                    break;
            }
        }
    }
}
