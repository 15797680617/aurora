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
public interface MethodMetadata {

    Method getMethod();

    String getMethodName();

    HttpMethod getHttpMethod();

    String getUrl();

    InvokeMode getInvokeMode();

    Map<Integer, String> getParamsIndex();

    Map<Integer, String> getHeadersIndex();

    int getBodyIndex();

    int getCallbackIndex();

    Class getResultType();

    Mapper getMapper();
}