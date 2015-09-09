
package com.wangtianya.abase.net.http;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class ABaseRequest {

    private String url;//必需
    private List<NameValuePair> formParams = new ArrayList<NameValuePair>();
    private Set<HttpListener> mHttpListeners = new HashSet<HttpListener>();
    private int httpMthod = HttpMethod.GET;
    private HttpController mHttpController;

    public ABaseRequest() {
    }

    public ABaseRequest(String url) {
        super();
        this.url = url;
    }

    public ABaseRequest(String url, List<NameValuePair> formParams) {
        super();
        this.url = url;
        this.formParams = formParams;
    }

    public ABaseRequest(String url, HttpListener httpListener) {
        super();
        this.url = url;
        this.mHttpListeners.add(httpListener);
    }

    public ABaseRequest(String url, List<NameValuePair> formParams, HttpListener httpListener) {
        super();
        this.url = url;
        this.formParams = formParams;
        this.mHttpListeners.add(httpListener);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<NameValuePair> getFormParams() {
        return formParams;
    }

    public void setFormParams(List<NameValuePair> formParams) {
        this.formParams = formParams;
    }

    public void addFormParam(String name, String value) {
        this.formParams.add(new BasicNameValuePair(name, value));
    }

    public void addFormParam(NameValuePair nameValuePair) {
        this.formParams.add(nameValuePair);
    }

    public void addAllFormParams(List<NameValuePair> formParams) {
        this.formParams.addAll(formParams);
    }

    public void clearFormParams() {
        this.formParams.clear();
    }

    public int getHttpMthod() {
        return httpMthod;
    }

    public void setHttpMthod(int httpMthod) {
        this.httpMthod = httpMthod;
    }

    public Set<HttpListener> getHttpListeners() {
        return mHttpListeners;
    }

    public void addHttpListener(HttpListener httpListener) {
        this.mHttpListeners.add(httpListener);
    }

    public void addAllHttpListener(Set<HttpListener> httpListeners) {
        this.mHttpListeners.addAll(httpListeners);
    }

    public HttpController getHttpController() {
        return mHttpController;
    }

    public void setHttpController(HttpController mHttpController) {
        this.mHttpController = mHttpController;
    }

    public static class HttpMethod {
        public static final int GET = 1;
        public static final int POST = 2;
    }

}
