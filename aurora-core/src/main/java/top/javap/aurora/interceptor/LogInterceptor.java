package top.javap.aurora.interceptor;

import top.javap.aurora.domain.HttpResponse;
import top.javap.aurora.invoke.Invocation;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/12
 **/
public class LogInterceptor implements AuroraInterceptor {

    @Override
    public <V> boolean before(Invocation invocation) {
        System.err.println("request url:" + invocation.getUrl());
        System.err.println("request params:" + invocation.getParams());
        System.err.println("request headers:" + invocation.getHeaders());
        System.err.println("request body:" + invocation.getBody());
        return true;
    }

    @Override
    public <V> void after(Invocation invocation, HttpResponse response) {
        System.err.println("response code:" + response.getCode());
        System.err.println("response message:" + response.getMessage());
        System.err.println("response headers:" + response.getHeaders());
        System.err.println("response body:" + response.getBody());
    }
}