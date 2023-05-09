package top.javap.aurora.invoke;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import top.javap.aurora.config.AuroraConfiguration;
import top.javap.aurora.domain.AsyncResult;
import top.javap.aurora.domain.Result;
import top.javap.aurora.domain.SyncResult;
import top.javap.aurora.enums.HttpMethod;
import top.javap.aurora.exception.AuroraException;
import top.javap.aurora.util.Maps;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/9
 **/
public class ApacheInvoker extends BaseInvoker {
    private final ExecutorService executor;
    private final HttpClient client;

    public ApacheInvoker(AuroraConfiguration configuration) {
        executor = configuration.getExecutorService();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout((int) configuration.getConnectTimeout())
                .setSocketTimeout((int) configuration.getReadTimeout())
                .build();
        client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
    }

    @Override
    protected Result doSync(Invocation invocation) {
        HttpRequestBase httpRequest = buildRequest(invocation);
        try {
            HttpResponse httpResponse = client.execute(httpRequest);
            return new SyncResult(triggerAfter(invocation, toHttpResponse(httpResponse)), null);
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
        try {
            client.execute(buildRequest(invocation), new ResponseHandler<Void>() {
                @Override
                public Void handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                    Object handle = invocation.getMethodMetadata()
                            .getMapper().getConfiguration()
                            .getResultHandler().handle(triggerAfter(invocation, toHttpResponse(httpResponse)), invocation.getResultType());
                    invocation.getCallback().onSuccess(handle);
                    return null;
                }
            });
        } catch (IOException e) {
            throw new AuroraException(null, e);
        }
    }

    private <V> HttpRequestBase buildRequest(Invocation invocation) {
        try {
            HttpRequestBase httpRequest;
            HttpMethod method = invocation.getHttpMethod();
            if (HttpMethod.GET.equals(method)) {
                httpRequest = new HttpGet(invocation.getFullUrl());
            } else if (HttpMethod.POST.equals(method)) {
                HttpPost httpPost = new HttpPost(invocation.getFullUrl());
                httpPost.setEntity(new StringEntity(invocation.getBody()));
                httpRequest = httpPost;
            } else {
                throw new AuroraException("invalid httpMethod:" + method);
            }
            if (Maps.notEmpty(invocation.getHeaders())) {
                invocation.getHeaders().entrySet().forEach(e -> httpRequest.addHeader(e.getKey(), e.getValue().toString()));
            }
            return httpRequest;
        } catch (UnsupportedEncodingException e) {
            throw new AuroraException("Unsupported Encoding", e);
        }
    }

    private top.javap.aurora.domain.HttpResponse toHttpResponse(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        top.javap.aurora.domain.HttpResponse httpResponse = new top.javap.aurora.domain.HttpResponse();
        httpResponse.setBody(EntityUtils.toString(entity));
        httpResponse.setCode(response.getStatusLine().getStatusCode());
        HeaderIterator headerIterator = response.headerIterator();
        while (headerIterator.hasNext()) {
            Header header = headerIterator.nextHeader();
            httpResponse.getHeaders().put(header.getName(), header.getValue());
        }
        return httpResponse;
    }

}