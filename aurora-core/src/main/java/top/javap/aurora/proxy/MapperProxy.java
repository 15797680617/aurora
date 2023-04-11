package top.javap.aurora.proxy;

import top.javap.aurora.domain.Mapper;
import top.javap.aurora.reflection.AuroraMethodFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/7
 **/
public class MapperProxy<T> implements InvocationHandler {

    private final Mapper<T> mapper;

    public MapperProxy(Mapper<T> mapper) {
        this.mapper = mapper;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Objects.equals(method.getDeclaringClass(), Object.class)) {
            return method.invoke(this, args);
        }
        return AuroraMethodFactory.getAuroraMethod(mapper, method).invoke(args);
    }
}