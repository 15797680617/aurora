package top.javap.aurora.executor;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.javap.aurora.config.AuroraConfiguration;
import top.javap.aurora.domain.AuroraRequest;
import top.javap.aurora.domain.AuroraResponse;
import top.javap.aurora.exception.AuroraException;
import top.javap.aurora.util.Maps;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/11
 **/
public class OkHttpExecutor extends BaseHttpExecutor {
    private final OkHttpClient httpClient;
    private final ExecutorService executorService;

    public OkHttpExecutor(AuroraConfiguration configuration) {
        super(configuration);
        executorService = new ThreadPoolExecutor(
                configuration.getCorePoolSize(),
                configuration.getMaxPoolSize(),
                configuration.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(configuration.getQueueSize())
        );
        httpClient = new OkHttpClient.Builder()
                .callTimeout(configuration.getTimeout(), TimeUnit.MILLISECONDS)
                .connectTimeout(configuration.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(configuration.getWriteTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(configuration.getReadTimeout(), TimeUnit.MILLISECONDS)
                .build();
    }

    @Override
    public AuroraResponse doExecute(AuroraRequest request) throws IOException {
        Response response = httpClient.newCall(buildRequest(request)).execute();
        return toHttpResponse(response);
    }

    @Override
    public AuroraFuture submit(AuroraRequest request) {
        AuroraFuture future = new AuroraFuture(() -> execute(request), request.getResultType());
        executorService.submit(future);
        return future;
    }

    @Override
    public <V> void submit(AuroraRequest<V> request, Callback<V> callback) {
        httpClient.newCall(buildRequest(request)).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(new AuroraException("request error", e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onSuccess(resultHandle(triggerAfter(request, toHttpResponse(response)), request.getResultType()));
            }
        });
    }

    private <V> Request buildRequest(AuroraRequest<V> request) {
        final Request.Builder builder = new Request.Builder().url(request.getFullUrl());
        switch (request.getMethod()) {
            case GET:
                builder.get();
                break;
            case POST:
                builder.post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"), request.getBody()));
                break;
            default:
                throw new AuroraException("invalid httoMethod:" + request.getMethod());
        }
        if (Maps.notEmpty(request.getHeaders())) {
            request.getHeaders().entrySet().forEach(e -> builder.header(e.getKey(), e.getValue().toString()));
        }
        return builder.build();
    }

    private AuroraResponse toHttpResponse(Response response) throws IOException {
        AuroraResponse auroraResponse = new AuroraResponse();
        auroraResponse.setBody(response.body().string());
        auroraResponse.setCode(response.code());
        auroraResponse.setMessage(response.message());
        response.headers().toMultimap().entrySet().forEach(e -> auroraResponse.getHeaders().put(e.getKey(), e.getValue().get(0)));
        return auroraResponse;
    }
}