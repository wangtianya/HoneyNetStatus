
package com.wangtianya.abase.net.http;

public interface HttpListener {

    void onstart(ABaseRequest mABaseRequest);

    void onSuccess(ABaseResponse response);

    void onFailed(ABaseResponse response);

    void onCompleted(ABaseResponse response, HttpTask httpTask);
}