package top.javap.aurora.domain;

import top.javap.aurora.executor.AuroraFuture;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/6
 **/
public interface Result {

    HttpResponse getResponse();

    boolean hasException();

    Throwable getException();

    default AuroraFuture getFuture() {
        throw new UnsupportedOperationException();
    }
}