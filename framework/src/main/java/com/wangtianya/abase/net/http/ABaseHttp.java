
package com.wangtianya.abase.net.http;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;

public class ABaseHttp {

    public HttpListener httpListener = new HttpListener() {

        @Override
        public void onstart(ABaseRequest mABaseRequest) {

        }

        @Override
        public void onSuccess(ABaseResponse response) {

        }

        @Override
        public void onFailed(ABaseResponse response) {

        }

        @Override
        public void onCompleted(ABaseResponse response, HttpTask httpTask) {
            mHttpSet.remove(httpTask);
        }
    };
    private Set<HttpTask> mHttpSet = new HashSet<HttpTask>();

    public ABaseHttp() {
    }

    public static ABaseHttp newInstance() {
        return new ABaseHttp();
    }

    public ABaseResponse get(String url) {
        ABaseRequest lightRequest = new ABaseRequest(url);
        HttpTask httpTask = new HttpTask(lightRequest);
        httpTask.request();
        return httpTask.request();
    }

    public ABaseResponse get(String url, List<NameValuePair> formParams) {
        ABaseRequest lightRequest = new ABaseRequest(url, formParams);
        HttpTask httpTask = new HttpTask(lightRequest);
        httpTask.request();
        return httpTask.request();
    }

    public HttpTask get(String url, HttpListener listener) {
        return get(new ABaseRequest(url, listener));
    }

    public HttpTask get(String url, List<NameValuePair> formParams, HttpListener listener) {
        return get(new ABaseRequest(url, formParams, listener));
    }

    public HttpTask get(ABaseRequest lightRequest) {
        lightRequest.addAllHttpListener(lightRequest.getHttpListeners());
        lightRequest.addHttpListener(httpListener);
        HttpTask httpTask = new HttpTask(lightRequest);
        httpTask.execute();
        return httpTask;
    }

    public ABaseResponse post(String url) {
        ABaseRequest lightRequest = new ABaseRequest(url);
        lightRequest.setHttpMthod(ABaseRequest.HttpMethod.POST);
        HttpTask httpTask = new HttpTask(lightRequest);
        httpTask.request();
        return httpTask.request();
    }

    public ABaseResponse post(String url, List<NameValuePair> formParams) {
        ABaseRequest lightRequest = new ABaseRequest(url, formParams);
        lightRequest.setHttpMthod(ABaseRequest.HttpMethod.POST);
        HttpTask httpTask = new HttpTask(lightRequest);
        httpTask.request();
        return httpTask.request();
    }

    public HttpTask post(String url, HttpListener listener) {
        return post(new ABaseRequest(url, listener));
    }

    public HttpTask post(String url, List<NameValuePair> formParams, HttpListener listener) {
        return post(new ABaseRequest(url, formParams, listener));
    }

    public HttpTask post(ABaseRequest lightRequest) {
        lightRequest.addHttpListener(httpListener);
        lightRequest.setHttpMthod(ABaseRequest.HttpMethod.POST);
        HttpTask httpTask = new HttpTask(lightRequest);
        httpTask.execute();
        return httpTask;
    }

    public boolean finish() {
        boolean isAllFinish = true;
        for (HttpTask httpTask : mHttpSet) {
            if (isAllFinish == false) {
                httpTask.cancel();
            }
        }
        return isAllFinish;
    }

}
