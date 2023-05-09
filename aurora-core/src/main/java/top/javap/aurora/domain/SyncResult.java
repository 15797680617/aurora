package top.javap.aurora.domain;

import java.util.Objects;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/6
 **/
public class SyncResult implements Result {

    private final HttpResponse response;
    private final Throwable exception;

    public SyncResult(HttpResponse response, Throwable exception) {
        this.response = response;
        this.exception = exception;
    }

    @Override
    public HttpResponse getResponse() {
        return response;
    }

    @Override
    public boolean hasException() {
        return Objects.nonNull(exception);
    }

    @Override
    public Throwable getException() {
        return exception;
    }
}