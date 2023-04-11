package top.javap.aurora.reflection;

import com.alibaba.fastjson.JSON;
import top.javap.aurora.domain.AuroraRequest;
import top.javap.aurora.domain.HttpResponse;
import top.javap.aurora.domain.Mapper;
import top.javap.aurora.enums.HttpMethod;
import top.javap.aurora.enums.InvokeMode;
import top.javap.aurora.exception.AuroraException;
import top.javap.aurora.executor.Callback;
import top.javap.aurora.executor.HttpExecutor;
import top.javap.aurora.util.Assert;
import top.javap.aurora.util.MethodUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
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
    private Class<?> returnType;
    private Type genericReturnType;
    private Map<Integer, String> paramIndex;
    private Map<Integer, String> headerIndex;
    private int bodyIndex = -1;
    private int callbackIndex = -1;
    private boolean async = false;
    private String url;
    private InvokeMode invokeMode;

    public AuroraMethod(Mapper mapper, Method method) {
        this.mapper = mapper;
        this.method = method;
        this.returnType = method.getReturnType();
        this.genericReturnType = method.getGenericReturnType();
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
        if (callbackIndex > -1) {
            this.genericReturnType = method.getParameters()[callbackIndex].getParameterizedType();
        }
    }

    void setInvokeMode(InvokeMode invokeMode) {
        this.invokeMode = invokeMode;
    }

    void setUrl(String url) {
        this.url = url;
    }

    public Object invoke(Object[] args) {
        final HttpExecutor httpExecutor = mapper.getConfiguration().getHttpExecutor();
        try {
            if (InvokeMode.SYNC.equals(invokeMode)) {
                HttpResponse httpResponse = httpExecutor.execute(buildAuroraRequest(args));
                return mapper.getConfiguration().getResultHandler().handle(httpResponse, returnType);
            } else if (InvokeMode.FUTURE.equals(invokeMode)) {
                return httpExecutor.submit(buildAuroraRequest(args));
            } else if (InvokeMode.CALLBACK.equals(invokeMode)) {
                Callback cb = (Callback) args[callbackIndex];
                httpExecutor.submit(buildAuroraRequest(args), cb);
                return null;
            } else {
                throw new AuroraException("invalid invoke mode:" + invokeMode);
            }
        } catch (Exception e) {
            throw new AuroraException("Aurora Exception:", e);
        }
    }

    private AuroraRequest buildAuroraRequest(Object[] args) {
        AuroraRequest request = new AuroraRequest(url, httpMethod);
        request.setReturnType(genericReturnType);
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