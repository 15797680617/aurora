package top.javap.aurora.reflection;

import top.javap.aurora.domain.Mapper;
import top.javap.aurora.enums.HttpMethod;
import top.javap.aurora.enums.InvokeMode;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/6
 **/
public class SimpleMethodMetadata implements MethodMetadata {

    private final Mapper mapper;
    private final Method method;
    private HttpMethod httpMethod;
    private Class resultType;
    private Map<Integer, String> paramsIndex;
    private Map<Integer, String> headersIndex;
    private int bodyIndex = -1;
    private int callbackIndex = -1;
    private boolean async = false;
    private String url;
    private InvokeMode invokeMode;

    public SimpleMethodMetadata(Mapper mapper, Method method) {
        this.mapper = mapper;
        this.method = method;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setResultType(Class resultType) {
        this.resultType = resultType;
    }

    public void setParamsIndex(Map<Integer, String> paramsIndex) {
        this.paramsIndex = paramsIndex;
    }

    public void setHeadersIndex(Map<Integer, String> headersIndex) {
        this.headersIndex = headersIndex;
    }

    public void setBodyIndex(int bodyIndex) {
        this.bodyIndex = bodyIndex;
    }

    public void setCallbackIndex(int callbackIndex) {
        this.callbackIndex = callbackIndex;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setInvokeMode(InvokeMode invokeMode) {
        this.invokeMode = invokeMode;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public String getMethodName() {
        return method.getName();
    }

    @Override
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public InvokeMode getInvokeMode() {
        return invokeMode;
    }

    @Override
    public Map<Integer, String> getParamsIndex() {
        return paramsIndex;
    }

    @Override
    public Map<Integer, String> getHeadersIndex() {
        return headersIndex;
    }

    @Override
    public int getBodyIndex() {
        return bodyIndex;
    }

    @Override
    public int getCallbackIndex() {
        return callbackIndex;
    }

    @Override
    public Class getResultType() {
        return resultType;
    }

    @Override
    public Mapper getMapper() {
        return mapper;
    }
}