package top.javap.aurora.executor;

import top.javap.aurora.config.AuroraConfiguration;
import top.javap.aurora.domain.SyncResult;
import top.javap.aurora.exception.AuroraException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/8
 **/
public class SimpleAuroraFuture<V> implements AuroraFuture<V> {

    private final CompletableFuture<SyncResult> completableFuture;
    private final Class<V> returnType;

    public SimpleAuroraFuture(CompletableFuture<SyncResult> completableFuture, Class<V> returnType) {
        this.completableFuture = completableFuture;
        this.returnType = returnType;
    }

    @Override
    public <V> V getResult(long timeout, TimeUnit unit) {
        try {
            return (V) AuroraConfiguration.configuration().getResultHandler().handle(completableFuture.get(timeout, unit).getResponse(), returnType);
        } catch (Exception e) {
            throw new AuroraException("result fetch failure", e);
        }
    }

    @Override
    public <V> V getResult() {
        try {
            return (V) AuroraConfiguration.configuration().getResultHandler().handle(completableFuture.get().getResponse(), returnType);
        } catch (Exception e) {
            throw new AuroraException("result fetch failure", e);
        }
    }
}