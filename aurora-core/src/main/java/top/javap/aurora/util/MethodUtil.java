package top.javap.aurora.util;

import top.javap.aurora.annotation.Get;
import top.javap.aurora.annotation.Post;
import top.javap.aurora.enums.HttpMethod;

import java.lang.reflect.Method;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/7
 **/
public final class MethodUtil {

    public static HttpMethod getHttpMethod(Method method) {
        if (method.isAnnotationPresent(Get.class)) {
            return HttpMethod.GET;
        }
        if (method.isAnnotationPresent(Post.class)) {
            return HttpMethod.POST;
        }
        return null;
    }

    public static boolean declareFromObject(Method method) {
        return method.getDeclaringClass().isAssignableFrom(Object.class);
    }
}