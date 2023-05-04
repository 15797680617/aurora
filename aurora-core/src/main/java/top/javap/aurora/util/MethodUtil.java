package top.javap.aurora.util;

import top.javap.aurora.annotation.Api;
import top.javap.aurora.enums.HttpMethod;
import top.javap.aurora.exception.AuroraException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/7
 **/
public final class MethodUtil {

    public static HttpMethod getHttpMethod(Method method) {
        if (method.isAnnotationPresent(Api.class)) {
            return HttpMethod.getByName(((Api) method.getAnnotation(Api.class)).method());
        }
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation.annotationType().isAnnotationPresent(Api.class)) {
                return HttpMethod.getByName(((Api) annotation.annotationType().getAnnotation(Api.class)).method());
            }
        }
        throw new AuroraException("invalid httpMethod");
    }

    public static boolean declareFromObject(Method method) {
        return method.getDeclaringClass().isAssignableFrom(Object.class);
    }
}