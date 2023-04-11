package top.javap.aurora.executor;

import top.javap.aurora.domain.AuroraRequest;
import top.javap.aurora.domain.HttpResponse;

import java.io.IOException;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/10
 **/
public interface HttpExecutor {

    <V> HttpResponse execute(AuroraRequest<V> request) throws IOException;

    <V> AuroraFuture<V> submit(AuroraRequest<V> request);

    <V> void submit(AuroraRequest request, Callback<V> callback);
}