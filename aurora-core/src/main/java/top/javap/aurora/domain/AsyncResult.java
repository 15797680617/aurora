package top.javap.aurora.domain;

import top.javap.aurora.executor.AuroraFuture;
import top.javap.aurora.executor.SimpleAuroraFuture;

import java.util.concurrent.CompletableFuture;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/6
 **/
public class AsyncResult implements Result {

    private final AuroraFuture future;

    public AsyncResult(CompletableFuture<SyncResult> completableFuture,Class returnType) {
        this.future = new SimpleAuroraFuture<SyncResult>(completableFuture, returnType);
    }

    @Override
    public HttpResponse getResponse() {
        return (HttpResponse) future.getResult();
    }

    @Override
    public boolean hasException() {
        return false;
    }

    @Override
    public Throwable getException() {
        return null;
    }

    @Override
    public AuroraFuture getFuture() {
        return future;
    }
}