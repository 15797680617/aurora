package top.javap.aurora.interceptor;

import top.javap.aurora.domain.HttpResponse;
import top.javap.aurora.invoke.Invocation;

/**
 * @Author: pch
 * @Date: 2023/4/11 20:08
 * @Description:
 */
public interface AuroraInterceptor {

    default <V> boolean before(Invocation invocation) {
        return true;
    }

    default <V> void after(Invocation invocation, HttpResponse response) {

    }
}