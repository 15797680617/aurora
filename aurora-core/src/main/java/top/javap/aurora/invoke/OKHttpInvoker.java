package top.javap.aurora.invoke;

import okhttp3.*;
import top.javap.aurora.config.AuroraConfiguration;
import top.javap.aurora.domain.AsyncResult;
import top.javap.aurora.domain.HttpResponse;
import top.javap.aurora.domain.Result;
import top.javap.aurora.domain.SyncResult;
import top.javap.aurora.exception.AuroraException;
import top.javap.aurora.util.Maps;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/6
 **/
public class OKHttpInvoker extends BaseInvoker {
    private final OkHttpClient httpClient;
    private final ExecutorService executor;

    public OKHttpInvoker(AuroraConfiguration configuration) {
        httpClient = new OkHttpClient.Builder()
                .callTimeout(configuration.getTimeout(), TimeUnit.MILLISECONDS)
                .connectTimeout(configuration.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(configuration.getWriteTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(configuration.getReadTimeout(), TimeUnit.MILLISECONDS)
                .build();
        executor = configuration.getExecutorService();
    }

    @Override
    protected Result doSync(Invocation invocation) {
        final Request request = buildRequest(invocation);
        try {
            Response response = httpClient.newCall(request).execute();
            return new SyncResult(triggerAfter(invocation, toHttpResponse(response)), null);
        } catch (Exception e) {
            return new SyncResult(null, e);
        }
    }

    @Override
    protected Result doFuture(Invocation invocation) {
        CompletableFuture<SyncResult> completableFuture = CompletableFuture.supplyAsync(() -> (SyncResult) this.doSync(invocation), executor);
        return new AsyncResult(completableFuture, invocation.getResultType());
    }

    @Override
    protected void doCallback(Invocation invocation) {
        httpClient.newCall(buildRequest(invocation)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                invocation.getCallback().onError(new AuroraException("error", e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Object handle = invocation.getMethodMetadata()
                        .getMapper().getConfiguration()
                        .getResultHandler().handle(triggerAfter(invocation, toHttpResponse(response)), invocation.getResultType());
                invocation.getCallback().onSuccess(handle);
            }
        });
    }


    private Request buildRequest(Invocation invocation) {
        final Request.Builder builder = new Request.Builder().url(invocation.getFullUrl());
        switch (invocation.getHttpMethod()) {
            case GET:
                builder.get();
                break;
            case POST:
                builder.post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"), invocation.getBody()));
                break;
            default:
                throw new AuroraException("invalid httoMethod:" + invocation.getHttpMethod());
        }
        if (Maps.notEmpty(invocation.getHeaders())) {
            invocation.getHeaders().entrySet().forEach(e -> builder.header(e.getKey(), e.getValue().toString()));
        }
        return builder.build();
    }

    private HttpResponse toHttpResponse(Response response) throws IOException {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setBody(response.body().string());
        httpResponse.setCode(response.code());
        httpResponse.setMessage(response.message());
        response.headers().toMultimap().entrySet().forEach(e -> httpResponse.getHeaders().put(e.getKey(), e.getValue().get(0)));
        return httpResponse;
    }
}