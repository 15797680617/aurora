package top.javap.aurora.invoke;

import top.javap.aurora.config.AuroraConfiguration;
import top.javap.aurora.enums.HttpClientEnum;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/6
 **/
public final class InvokerFactory {

    private static final ConcurrentMap<HttpClientEnum, Invoker> CACHE = new ConcurrentHashMap<>();
    private static final Object MUTEX = new Object();

    public static <T> Invoker getInvoker(AuroraConfiguration configuration) {
        final HttpClientEnum clientEnum = configuration.getHttpClientEnum();
        Invoker invoker = CACHE.get(clientEnum);
        if (Objects.isNull(invoker)) {
            synchronized (MUTEX) {
                invoker = CACHE.get(clientEnum);
                if (Objects.isNull(invoker)) {
                    CACHE.put(clientEnum, invoker = createInvoker(configuration, clientEnum));
                }
            }
        }
        return invoker;
    }

    private static <T> Invoker createInvoker(AuroraConfiguration configuration, HttpClientEnum clientEnum) {
        if (HttpClientEnum.OKHTTP.equals(clientEnum)) {
            return new OKHttpInvoker(configuration);
        } else if (HttpClientEnum.APACHE.equals(clientEnum)) {
            return new ApacheInvoker(configuration);
        }
        return null;
    }
}