package top.javap.aurora.invoke;

import top.javap.aurora.enums.HttpMethod;
import top.javap.aurora.enums.InvokeMode;
import top.javap.aurora.executor.Callback;
import top.javap.aurora.reflection.MethodMetadata;

import java.util.Map;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/6
 **/
public interface Invocation {

    String getUrl();

    String getFullUrl();

    HttpMethod getHttpMethod();

    InvokeMode getInvokeMode();

    Map<String, Object> getParams();

    Map<String, Object> getHeaders();

    String getBody();

    <V> Class<V> getResultType();

    <V> Callback<V> getCallback();

    MethodMetadata getMethodMetadata();
}