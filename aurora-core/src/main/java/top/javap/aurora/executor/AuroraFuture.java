package top.javap.aurora.executor;

import java.util.concurrent.TimeUnit;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/11
 **/
public interface AuroraFuture<V> {

    <V> V getResult();

    <V> V getResult(long timeout, TimeUnit unit);
}