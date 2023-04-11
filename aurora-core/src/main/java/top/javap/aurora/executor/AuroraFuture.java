package top.javap.aurora.executor;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/11
 **/
public class AuroraFuture<V> extends FutureTask<V> {

    public AuroraFuture(@NotNull Callable<V> callable) {
        super(callable);
    }

    public AuroraFuture(@NotNull Runnable runnable, V result) {
        super(runnable, result);
    }
}