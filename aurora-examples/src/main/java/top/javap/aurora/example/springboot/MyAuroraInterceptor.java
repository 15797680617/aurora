package top.javap.aurora.example.springboot;

import org.springframework.stereotype.Component;
import top.javap.aurora.domain.AuroraRequest;
import top.javap.aurora.domain.AuroraResponse;
import top.javap.aurora.interceptor.AuroraInterceptor;
import top.javap.aurora.reflection.AuroraMethod;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/13
 **/
@Component
public class MyAuroraInterceptor implements AuroraInterceptor {

    @Override
    public <V> boolean before(AuroraMethod<V> method, AuroraRequest<V> request, Object[] args) {
        System.err.println("before");
        return true;
    }

    @Override
    public <V> void after(AuroraRequest<V> request, AuroraResponse response) {
        System.err.println("after");
    }
}