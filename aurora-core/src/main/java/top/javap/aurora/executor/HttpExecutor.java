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

    HttpResponse execute(AuroraRequest request) throws IOException;

    <V> AuroraFuture<V> submit(AuroraRequest request);

    <T> void submit(AuroraRequest request, Callback<T> callback);
}