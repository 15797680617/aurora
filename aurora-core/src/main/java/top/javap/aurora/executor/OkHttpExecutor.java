package top.javap.aurora.executor;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.javap.aurora.domain.AuroraRequest;
import top.javap.aurora.domain.HttpResponse;
import top.javap.aurora.exception.AuroraException;
import top.javap.aurora.util.Maps;

import java.io.IOException;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/11
 **/
public class OkHttpExecutor implements HttpExecutor {
    private final OkHttpClient httpClient = new OkHttpClient();

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
    public <V> AuroraFuture<V> submit(AuroraRequest request) {
        return null;
    }

    @Override
    public <T> void submit(AuroraRequest request, Callback<T> callback) {

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