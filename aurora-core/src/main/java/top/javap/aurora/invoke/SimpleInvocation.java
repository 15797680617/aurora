package top.javap.aurora.invoke;

import com.alibaba.fastjson.JSON;
import top.javap.aurora.enums.HttpMethod;
import top.javap.aurora.enums.InvokeMode;
import top.javap.aurora.executor.Callback;
import top.javap.aurora.reflection.MethodMetadata;
import top.javap.aurora.util.Maps;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/6
 **/
public class SimpleInvocation implements Invocation {

    private final MethodMetadata methodMetadata;
    private final Object[] args;

    public SimpleInvocation(MethodMetadata methodMetadata, Object[] args) {
        this.methodMetadata = methodMetadata;
        this.args = args;
    }

    @Override
    public String getUrl() {
        return methodMetadata.getUrl();
    }

    @Override
    public String getFullUrl() {
        String url = getUrl();
        if (Maps.notEmpty(getParams())) {
            boolean first = true;
            for (Map.Entry<String, Object> entry : getParams().entrySet()) {
                url += first ? "?" : "&";
                url += entry.getKey() + "=" + entry.getValue();
                first = false;
            }
        }
        return url;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return methodMetadata.getHttpMethod();
    }

    @Override
    public InvokeMode getInvokeMode() {
        return methodMetadata.getInvokeMode();
    }

    @Override
    public Map<String, Object> getParams() {
        return new HashMap<>();
    }

    @Override
    public Map<String, Object> getHeaders() {
        return new HashMap<>();
    }

    @Override
    public String getBody() {
        if (methodMetadata.getBodyIndex() > -1) {
            Object body = args[methodMetadata.getBodyIndex()];
            if (body instanceof String) {
                return (String) body;
            }
            return JSON.toJSONString(body);
        }
        return null;
    }

    @Override
    public <V> Class<V> getResultType() {
        return methodMetadata.getResultType();
    }

    @Override
    public <V> Callback<V> getCallback() {
        return (Callback<V>) args[methodMetadata.getCallbackIndex()];
    }

    @Override
    public MethodMetadata getMethodMetadata() {
        return methodMetadata;
    }
}