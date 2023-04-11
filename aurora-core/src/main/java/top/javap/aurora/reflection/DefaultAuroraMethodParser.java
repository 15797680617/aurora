package top.javap.aurora.reflection;

import top.javap.aurora.annotation.Get;
import top.javap.aurora.annotation.Header;
import top.javap.aurora.annotation.Param;
import top.javap.aurora.annotation.Post;
import top.javap.aurora.annotation.RequestBody;
import top.javap.aurora.domain.Mapper;
import top.javap.aurora.enums.InvokeMode;
import top.javap.aurora.executor.AuroraFuture;
import top.javap.aurora.executor.Callback;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/7
 **/
public class DefaultAuroraMethodParser implements AuroraMethodParser {

    @Override
    public AuroraMethod parse(Mapper mapper, Method method) {
        AuroraMethod auroraMethod = new AuroraMethod(mapper, method);
        auroraMethod.setParamIndex(buildParamIndex(method));
        auroraMethod.setHeaderIndex(buildHeaderIndex(method));
        auroraMethod.setBodyIndex(getBodyIndex(method));
        auroraMethod.setCallbackIndex(getCallbackIndex(method));
        auroraMethod.setUrl(getUrl(method));
        auroraMethod.setInvokeMode(getInvokeMode(method));
        return auroraMethod;
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
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof Get) {
                return ((Get) annotation).value();
            }
            if (annotation instanceof Post) {
                return ((Post) annotation).value();
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

    private Map<Integer, String> buildParamIndex(Method method) {
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

    private Map<Integer, String> buildHeaderIndex(Method method) {
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
}