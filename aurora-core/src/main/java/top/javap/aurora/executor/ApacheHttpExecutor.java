package top.javap.aurora.executor;

import top.javap.aurora.domain.AuroraRequest;
import top.javap.aurora.domain.HttpResponse;

import java.io.IOException;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/11
 **/
// todo
public class ApacheHttpExecutor implements HttpExecutor {

    @Override
    public <V> HttpResponse execute(AuroraRequest<V> request) throws IOException {
        return null;
    }

    @Override
    public <V> AuroraFuture<V> submit(AuroraRequest<V> request) {
        return null;
    }

    @Override
    public <V> void submit(AuroraRequest request, Callback<V> callback) {

    }
}