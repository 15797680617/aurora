package top.javap.aurora.interceptor;

import top.javap.aurora.domain.AuroraRequest;
import top.javap.aurora.domain.AuroraResponse;
import top.javap.aurora.reflection.AuroraMethod;

/**
 * @Author: pch
 * @Date: 2023/4/11 20:08
 * @Description:
 */
public interface AuroraInterceptor {

    default <V> boolean before(AuroraMethod<V> method, AuroraRequest<V> request, Object[] args) {
        return true;
    }

    default <V> void after(AuroraRequest<V> request, AuroraResponse response) {

    }
}