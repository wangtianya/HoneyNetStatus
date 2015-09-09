
package com.wangtianya.abase.net.http;

import org.apache.http.HttpResponse;

public class ABaseResponse {

    private boolean isSuccess = false;
    private String errorMsg = "";
    private String textContent = null;

    private ABaseRequest request;
    private HttpResponse httpResponse;

    public ABaseResponse() {
        super();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getTextContent() {
        if (textContent != null) {
            return textContent;
        }
        return null;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }


    public ABaseRequest getRequest() {
        return request;
    }

    public void setRequest(ABaseRequest request) {
        this.request = request;
    }

    public HttpResponse getResponse() {
        return httpResponse;
    }

    public void setResponse(HttpResponse response) {
        this.httpResponse = response;
    }
}
