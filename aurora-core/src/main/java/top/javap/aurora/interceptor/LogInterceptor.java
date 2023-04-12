package top.javap.aurora.interceptor;

import top.javap.aurora.domain.AuroraRequest;
import top.javap.aurora.domain.AuroraResponse;
import top.javap.aurora.reflection.AuroraMethod;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/12
 **/
public class LogInterceptor implements AuroraInterceptor {

    @Override
    public <V> boolean before(AuroraMethod<V> method, AuroraRequest<V> request, Object[] args) {
        System.err.println("request url:" + request.getUrl());
        System.err.println("request params:" + request.getParams());
        System.err.println("request headers:" + request.getHeaders());
        System.err.println("request body:" + request.getBody());
        return true;
    }

    @Override
    public <V> void after(AuroraRequest<V> request, AuroraResponse response) {
        System.err.println("response code:" + response.getCode());
        System.err.println("response message:" + response.getMessage());
        System.err.println("response headers:" + response.getHeaders());
        System.err.println("response body:" + response.getBody());
    }
}