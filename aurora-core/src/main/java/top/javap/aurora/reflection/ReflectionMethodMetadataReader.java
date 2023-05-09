package top.javap.aurora.reflection;

import top.javap.aurora.annotation.*;
import top.javap.aurora.domain.Mapper;
import top.javap.aurora.enums.InvokeMode;
import top.javap.aurora.exception.AuroraException;
import top.javap.aurora.executor.AuroraFuture;
import top.javap.aurora.executor.Callback;
import top.javap.aurora.util.MethodUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 通过反射读取方法元数据
 *
 * @author: pch
 * @description:
 * @date: 2023/5/6
 **/
public class ReflectionMethodMetadataReader extends BaseMethodMetadataReader {

    @Override
    protected MethodMetadata createMethodMetadata(Mapper mapper, Method method) {
        SimpleMethodMetadata methodMetadata = new SimpleMethodMetadata(mapper, method);
        methodMetadata.setHttpMethod(MethodUtil.getHttpMethod(method));
        methodMetadata.setParamsIndex(buildParamsIndex(method));
        methodMetadata.setHeadersIndex(buildHeadersIndex(method));
        methodMetadata.setBodyIndex(getBodyIndex(method));
        methodMetadata.setCallbackIndex(getCallbackIndex(method));
        methodMetadata.setUrl(getUrl(method));
        methodMetadata.setInvokeMode(getInvokeMode(method));
        methodMetadata.setResultType(getResultType(methodMetadata, method));
        return methodMetadata;
    }

    private <V> Class<V> getResultType(MethodMetadata methodMetadata, Method method) {
        switch (methodMetadata.getInvokeMode()) {
            case SYNC:
                return (Class<V>) method.getReturnType();
            case FUTURE:
                return (Class<V>) ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
            case CALLBACK:
                return (Class<V>) ((ParameterizedType) method.getParameters()[methodMetadata.getCallbackIndex()].getParameterizedType())
                        .getActualTypeArguments()[0];
            default:
                throw new AuroraException("invalid invokeMode:" + methodMetadata.getInvokeMode());
        }
    }


    private InvokeMode getInvokeMode(Method method) {
        if (getCallbackIndex(method) > -1) {
            return InvokeMode.CALLBACK;
        }
        if (method.getReturnType().isAssignableFrom(AuroraFuture.class)) {
            return InvokeMode.FUTURE;
        }
        return InvokeMode.SYNC;
    }


    private String getUrl(Method method) {
        String baseUrl = method.getDeclaringClass().getAnnotation(top.javap.aurora.annotation.Mapper.class).baseUrl();
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof Api) {
                return baseUrl + ((Api) annotation).url();
            }
            if (annotation instanceof Get) {
                return baseUrl + ((Get) annotation).value();
            }
            if (annotation instanceof Post) {
                return baseUrl + ((Post) annotation).value();
            }
        }
        return null;
    }


    private int getCallbackIndex(Method method) {
        final Parameter[] parameters = method.getParameters();
        if (Objects.nonNull(parameters) && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                if (parameter.getType().isAssignableFrom(Callback.class)) {
                    return i;
                }
            }
        }
        return -1;
    }


    private int getBodyIndex(Method method) {
        final Parameter[] parameters = method.getParameters();
        if (Objects.nonNull(parameters) && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                if (parameter.isAnnotationPresent(RequestBody.class)) {
                    return i;
                }
            }
        }
        return -1;
    }


    private Map<Integer, String> buildHeadersIndex(Method method) {
        final Map<Integer, String> headerIndex = new HashMap<>();
        final Parameter[] parameters = method.getParameters();
        if (Objects.nonNull(parameters) && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                if (parameter.isAnnotationPresent(Header.class)) {
                    String key = parameter.getAnnotation(Header.class).value();
                    headerIndex.put(i, key);
                }
            }
        }
        return headerIndex;
    }


    private Map<Integer, String> buildParamsIndex(Method method) {
        final Map<Integer, String> paramIndex = new HashMap<>();
        final Parameter[] parameters = method.getParameters();
        if (Objects.nonNull(parameters) && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                if (parameter.isAnnotationPresent(Param.class)) {
                    String key = parameter.getAnnotation(Param.class).value();
                    paramIndex.put(i, key);
                }
            }
        }
        return paramIndex;
    }

}