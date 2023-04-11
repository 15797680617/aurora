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
import java.util.Map;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/10
 **/
public class AuroraMethod<V> {

    private final Mapper mapper;
    private final Method method;
    private final HttpMethod httpMethod;
    private Class<V> resultType;
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
    }

    public int getCallbackIndex() {
        return callbackIndex;
    }

    void setInvokeMode(InvokeMode invokeMode) {
        this.invokeMode = invokeMode;
    }

    public InvokeMode getInvokeMode() {
        return invokeMode;
    }

    public void setResultType(Class<V> resultType) {
        this.resultType = resultType;
    }

    void setUrl(String url) {
        this.url = url;
    }

    public Object invoke(Object[] args) {
        final HttpExecutor httpExecutor = mapper.getConfiguration().getHttpExecutor();
        final AuroraRequest<V> request = buildAuroraRequest(args);
        try {
            if (InvokeMode.SYNC.equals(invokeMode)) {
                HttpResponse httpResponse = httpExecutor.execute(request);
                return mapper.getConfiguration().getResultHandler().handle(httpResponse, resultType);
            } else if (InvokeMode.FUTURE.equals(invokeMode)) {
                return httpExecutor.submit(request);
            } else if (InvokeMode.CALLBACK.equals(invokeMode)) {
                Callback cb = (Callback) args[callbackIndex];
                httpExecutor.submit(request, cb);
                return null;
            } else {
                throw new AuroraException("invalid invoke mode:" + invokeMode);
            }
        } catch (Exception e) {
            throw new AuroraException("Aurora Exception:", e);
        }
    }

    private AuroraRequest<V> buildAuroraRequest(Object[] args) {
        AuroraRequest<V> request = new AuroraRequest(url, httpMethod);
        request.setResultType(resultType);
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