package top.javap.aurora.executor;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import top.javap.aurora.config.AuroraConfiguration;
import top.javap.aurora.domain.AuroraRequest;
import top.javap.aurora.domain.AuroraResponse;
import top.javap.aurora.enums.HttpMethod;
import top.javap.aurora.exception.AuroraException;
import top.javap.aurora.util.Maps;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/11
 **/
public class ApacheHttpExecutor extends BaseHttpExecutor {

    private final ExecutorService executor;
    private final HttpClient client;

    public ApacheHttpExecutor(AuroraConfiguration configuration) {
        super(configuration);
        executor = new ThreadPoolExecutor(
                configuration.getCorePoolSize(),
                configuration.getMaxPoolSize(),
                configuration.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(configuration.getQueueSize())
        );
        client = HttpClientBuilder.create().build();
    }


    @Override
    protected <V> AuroraResponse doExecute(AuroraRequest<V> request) throws IOException {
        HttpRequestBase httpRequest = buildRequest(request);
        HttpResponse httpResponse = client.execute(httpRequest);
        return toAuroraResponse(httpResponse);
    }

    private AuroraResponse toAuroraResponse(HttpResponse response) {
        try {
            HttpEntity entity = response.getEntity();
            AuroraResponse auroraResponse = new AuroraResponse();
            auroraResponse.setBody(EntityUtils.toString(entity));
            auroraResponse.setCode(response.getStatusLine().getStatusCode());
            HeaderIterator headerIterator = response.headerIterator();
            while (headerIterator.hasNext()) {
                Header header = headerIterator.nextHeader();
                auroraResponse.getHeaders().put(header.getName(), header.getValue());
            }
            return auroraResponse;
        } catch (IOException e) {
            throw new AuroraException("IOException", e);
        }
    }

    private <V> HttpRequestBase buildRequest(AuroraRequest<V> request) {
        try {
            HttpRequestBase httpRequest;
            HttpMethod method = request.getMethod();
            if (HttpMethod.GET.equals(method)) {
                httpRequest = new HttpGet(request.getFullUrl());
            } else if (HttpMethod.POST.equals(method)) {
                HttpPost httpPost = new HttpPost(request.getFullUrl());
                httpPost.setEntity(new StringEntity(request.getBody()));
                httpRequest = httpPost;
            } else {
                throw new AuroraException("invalid httpMethod:" + method);
            }
            if (Maps.notEmpty(request.getHeaders())) {
                request.getHeaders().entrySet().forEach(e -> httpRequest.addHeader(e.getKey(), e.getValue().toString()));
            }
            return httpRequest;
        } catch (UnsupportedEncodingException e) {
            throw new AuroraException("Unsupported Encoding", e);
        }
    }

    @Override
    public <V> AuroraFuture<V> submit(AuroraRequest<V> request) {
        return null;
    }

    @Override
    public <V> void submit(AuroraRequest<V> request, Callback<V> callback) {

    }
}