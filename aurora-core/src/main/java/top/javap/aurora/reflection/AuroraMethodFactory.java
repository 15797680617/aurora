package top.javap.aurora.reflection;

import top.javap.aurora.domain.Mapper;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/11
 **/
public final class AuroraMethodFactory {

    private static final ConcurrentMap<Method, AuroraMethod> METHOD_CACHE = new ConcurrentHashMap<>();

    public static AuroraMethod getAuroraMethod(Mapper mapper, Method method) {
        AuroraMethod auroraMethod = METHOD_CACHE.get(method);
        if (Objects.isNull(auroraMethod)) {
            synchronized (METHOD_CACHE) {
                auroraMethod = METHOD_CACHE.get(method);
                if (Objects.isNull(auroraMethod)) {
                    METHOD_CACHE.put(method, auroraMethod = createAuroraMethod(mapper, method));
                }
            }
        }
        return auroraMethod;
    }

    private static <V> AuroraMethod<V> createAuroraMethod(Mapper mapper, Method method) {
        return mapper.getConfiguration().getAuroraMethodParser().parse(mapper, method);
    }
}