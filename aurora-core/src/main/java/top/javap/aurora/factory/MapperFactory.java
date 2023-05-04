package top.javap.aurora.factory;

import top.javap.aurora.config.AuroraConfiguration;
import top.javap.aurora.domain.Mapper;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/4
 **/
public class MapperFactory {

    private static final ConcurrentMap<Class, Object> PROXY_CACHE = new ConcurrentHashMap<>();
    private static final Object MUTEX = new Object();
    private static final AuroraConfiguration auroraConfiguration;

    static {
        auroraConfiguration = AuroraConfiguration.configuration();
    }

    public static <T> T getInstance(Class<T> mapperClass) {
        Object proxy = PROXY_CACHE.get(mapperClass);
        if (Objects.isNull(proxy)) {
            synchronized (MUTEX) {
                proxy = PROXY_CACHE.get(mapperClass);
                if (Objects.isNull(proxy)) {
                    PROXY_CACHE.put(mapperClass, proxy = createInstance(mapperClass));
                }
            }
        }
        return (T) proxy;
    }

    private static <T> T createInstance(Class<T> mapperClass) {
        return new Mapper<>(mapperClass, auroraConfiguration).getProxy();
    }

    public static AuroraConfiguration config() {
        return auroraConfiguration;
    }
}