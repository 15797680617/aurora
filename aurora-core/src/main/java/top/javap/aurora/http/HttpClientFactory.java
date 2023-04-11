package top.javap.aurora.http;

import top.javap.aurora.config.AuroraConfiguration;
import top.javap.aurora.enums.HttpClientEnum;
import top.javap.aurora.exception.AuroraException;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/10
 **/
public final class HttpClientFactory {

    private static final ConcurrentMap<HttpClientEnum, HttpClient> CLIENT_CACHE = new ConcurrentHashMap<>();

    public static HttpClient getHttpClient(AuroraConfiguration configuration) {
        final HttpClientEnum httpClientEnum = configuration.getHttpClientEnum();
        HttpClient httpClient = CLIENT_CACHE.get(httpClientEnum);
        if (Objects.isNull(httpClient)) {
            synchronized (HttpClientFactory.class) {
                httpClient = CLIENT_CACHE.get(httpClientEnum);
                if (Objects.isNull(httpClient)) {
                    httpClient = createHttpClient(configuration);
                }
            }
        }
        return httpClient;
    }

    private static HttpClient createHttpClient(AuroraConfiguration configuration) {
        final HttpClientEnum httpClientEnum = configuration.getHttpClientEnum();
        if (HttpClientEnum.OKHTTP.equals(httpClientEnum)) {
            return new OkHttpClientAdapter();
        }
        throw new AuroraException("invalid HttpClientEnum:" + httpClientEnum);
    }


}