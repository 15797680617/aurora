package top.javap.aurora.executor;

import top.javap.aurora.config.AuroraConfiguration;
import top.javap.aurora.domain.AuroraResponse;
import top.javap.aurora.exception.AuroraException;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/11
 **/
public class AuroraFuture<V> extends FutureTask<AuroraResponse> {

    private Class<V> returnType;

    public AuroraFuture(Callable callable, Class<V> returnType) {
        super(callable);
        this.returnType = returnType;
    }

    public <V> V getResult() {
        try {
            return (V) AuroraConfiguration.configuration().getResultHandler().handle(get(), returnType);
        } catch (Exception e) {
            throw new AuroraException("Result fetch failure", e);
        }
    }
}