package top.javap.aurora.reflection;

import com.alibaba.fastjson.JSON;
import top.javap.aurora.domain.AuroraRequest;
import top.javap.aurora.domain.HttpResponse;
import top.javap.aurora.domain.Mapper;
import top.javap.aurora.enums.HttpMethod;
import top.javap.aurora.exception.AuroraException;
import top.javap.aurora.executor.OkHttpExecutor;
import top.javap.aurora.util.Assert;
import top.javap.aurora.util.MethodUtil;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/10
 **/
public class AuroraMethod {

    private final Mapper mapper;
    private final Method method;
    private final HttpMethod httpMethod;
    private final Class<?> returnType;
    private Map<Integer, String> paramIndex;
    private Map<Integer, String> headerIndex;
    private int bodyIndex = -1;
    private int callbackIndex = -1;
    private boolean async = false;
    private String url;

    public AuroraMethod(Mapper mapper, Method method) {
        this.mapper = mapper;
        this.method = method;
        this.returnType = method.getReturnType();
        Assert.notNull(httpMethod = MethodUtil.getHttpMethod(method), "invalid HttpMethod:" + method);
    }

    void setParamIndex(Map<Integer, String> paramIndex) {
        this.paramIndex = paramIndex;
    }

    void setHeaderIndex(Map<Integer, String> headerIndex) {
        this.headerIndex = headerIndex;
    }

    void setBodyIndex(int bodyIndex) {
        this.bodyIndex = bodyIndex;
    }

    void setCallbackIndex(int callbackIndex) {
        this.callbackIndex = callbackIndex;
        this.setAsync(callbackIndex > -1);
    }

    void setAsync(boolean async) {
        this.async = async;
    }

    void setUrl(String url) {
        this.url = url;
    }

    public Object invoke(Object[] args) {
        try {
            HttpResponse httpResponse = new OkHttpExecutor().execute(buildAuroraRequest(args));
            return mapper.getConfiguration().getResultHandler().handle(httpResponse, returnType);
        } catch (Exception e) {
            throw new AuroraException("Aurora Exception:", e);
        }
    }

    private AuroraRequest buildAuroraRequest(Object[] args) {
        AuroraRequest request = new AuroraRequest(url, httpMethod);
        paramIndex.entrySet().forEach(e -> {
            request.getParams().put(e.getValue(), args[e.getKey()]);
        });
        headerIndex.entrySet().forEach(e -> {
            request.getHeaders().put(e.getValue(), args[e.getKey()]);
        });
        if (bodyIndex > -1) {
            Object body = args[bodyIndex];
            if (body instanceof Character) {
                request.setBody(body.toString());
            } else {
                request.setBody(JSON.toJSONString(body));
            }
        }
        return request;
    }
}