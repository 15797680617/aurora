package top.javap.aurora.executor;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import top.javap.aurora.config.AuroraConfiguration;
import top.javap.aurora.domain.AuroraRequest;
import top.javap.aurora.domain.HttpResponse;
import top.javap.aurora.exception.AuroraException;
import top.javap.aurora.util.Maps;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/11
 **/
public class OkHttpExecutor implements HttpExecutor {
    private final AuroraConfiguration configuration;
    private final OkHttpClient httpClient = new OkHttpClient();
    private final ExecutorService executorService;

    public OkHttpExecutor(AuroraConfiguration configuration) {
        this.configuration = configuration;
        executorService = new ThreadPoolExecutor(
                configuration.getCorePoolSize(),
                configuration.getMaxPoolSize(),
                configuration.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(configuration.getQueueSize())
        );
    }

    @Override
    public HttpResponse execute(AuroraRequest request) throws IOException {
        Response response = httpClient.newCall(buildRequest(request)).execute();
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setBody(response.body().string());
        httpResponse.setCode(response.code());
        httpResponse.setMessage(response.message());
        return httpResponse;
    }

    @Override
    public AuroraFuture submit(AuroraRequest request) {
        AuroraFuture future = new AuroraFuture(() -> execute(request), request.getReturnType());
        executorService.submit(future);
        return future;
    }

    @Override
    public <T> void submit(AuroraRequest request, Callback<T> callback) {
        httpClient.newCall(buildRequest(request)).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onError(new AuroraException("request error", e));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                HttpResponse httpResponse = new HttpResponse();
                httpResponse.setBody(response.body().string());
                httpResponse.setCode(response.code());
                httpResponse.setMessage(response.message());
                callback.onSuccess((T) configuration.getResultHandler().handle(httpResponse,
                        ((Class) ((ParameterizedType) request.getReturnType()).getActualTypeArguments()[0])));
            }
        });
    }

    private Request buildRequest(AuroraRequest request) {
        final Request.Builder builder = new Request.Builder().url(request.getFullUrl());
        switch (request.getMethod()) {
            case GET:
                builder.get();
                break;
            case POST:
                builder.post(RequestBody.create(request.getBody(), MediaType.parse("application/json;charset=utf-8")));
                break;
            default:
                throw new AuroraException("invalid httoMethod:" + request.getMethod());
        }
        if (Maps.notEmpty(request.getHeaders())) {
            request.getHeaders().entrySet().forEach(e -> builder.header(e.getKey(), e.getValue().toString()));
        }
        return builder.build();
    }
}