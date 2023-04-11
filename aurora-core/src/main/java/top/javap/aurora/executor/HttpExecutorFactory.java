package top.javap.aurora.executor;

import top.javap.aurora.config.AuroraConfiguration;
import top.javap.aurora.enums.HttpClientEnum;
import top.javap.aurora.exception.AuroraException;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/11
 **/
public final class HttpExecutorFactory {
    private static final ConcurrentMap<HttpClientEnum, HttpExecutor> HTTP_EXECUTOR_CACHE = new ConcurrentHashMap<>();
    private static final Object MUTEX = new Object();

    public static HttpExecutor getHttpExecutor(HttpClientEnum clientEnum, AuroraConfiguration configuration) {
        HttpExecutor executor = HTTP_EXECUTOR_CACHE.get(clientEnum);
        if (Objects.isNull(executor)) {
            synchronized (MUTEX) {
                executor = HTTP_EXECUTOR_CACHE.get(clientEnum);
                if (Objects.isNull(executor)) {
                    HTTP_EXECUTOR_CACHE.put(clientEnum, executor = createHttpExecutor(clientEnum, configuration));
                }
            }
        }
        return executor;
    }

    private static HttpExecutor createHttpExecutor(HttpClientEnum clientEnum, AuroraConfiguration configuration) {
        if (HttpClientEnum.OKHTTP.equals(clientEnum)) {
            return new OkHttpExecutor(configuration);
        } else if (HttpClientEnum.APACHE.equals(clientEnum)) {
            throw new AuroraException("not supported HttpClientEnum:" + clientEnum);
        } else {
            throw new AuroraException("invalid HttpClientEnum:" + clientEnum);
        }
    }
}