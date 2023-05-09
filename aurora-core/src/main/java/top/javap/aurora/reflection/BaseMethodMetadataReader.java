package top.javap.aurora.reflection;

import top.javap.aurora.domain.Mapper;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/6
 **/
public abstract class BaseMethodMetadataReader implements MethodMetadataReader {

    private final ConcurrentMap<Method, MethodMetadata> CACHE = new ConcurrentHashMap<>();
    private final Object MUTEX = new Object();

    @Override
    public final MethodMetadata getMethodMetadata(Mapper mapper, Method method) {
        MethodMetadata methodMetadata = CACHE.get(method);
        if (Objects.isNull(methodMetadata)) {
            synchronized (MUTEX) {
                methodMetadata = CACHE.get(method);
                if (Objects.isNull(methodMetadata)) {
                    CACHE.put(method, methodMetadata = createMethodMetadata(mapper, method));
                }
            }
        }
        return methodMetadata;
    }

    protected abstract MethodMetadata createMethodMetadata(Mapper mapper, Method method);
}