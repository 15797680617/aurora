package top.javap.aurora.example.springboot;

import org.springframework.stereotype.Component;
import top.javap.aurora.domain.HttpResponse;
import top.javap.aurora.interceptor.AuroraInterceptor;
import top.javap.aurora.invoke.Invocation;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/13
 **/
@Component
public class MyAuroraInterceptor implements AuroraInterceptor {

    @Override
    public <V> boolean before(Invocation invocation) {
        System.err.println("before");
        return true;
    }

    @Override
    public <V> void after(Invocation invocation, HttpResponse response) {
        System.err.println("after");
    }
}