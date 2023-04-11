package top.javap.aurora.domain;

import top.javap.aurora.enums.HttpMethod;

import java.util.Map;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/7
 **/
public class RequestData {

    private final String url;
    private final HttpMethod method;
    private Map<String, Object> params;
    private Map<String, Object> headers;
    private String body;

    public RequestData(String url, HttpMethod method) {
        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}