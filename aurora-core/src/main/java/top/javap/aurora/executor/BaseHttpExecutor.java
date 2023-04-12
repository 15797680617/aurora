package top.javap.aurora.executor;

import top.javap.aurora.config.AuroraConfiguration;
import top.javap.aurora.domain.AuroraRequest;
import top.javap.aurora.domain.AuroraResponse;

import java.io.IOException;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/12
 **/
public abstract class BaseHttpExecutor implements HttpExecutor {

    protected final AuroraConfiguration configuration;

    protected BaseHttpExecutor(AuroraConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <V> AuroraResponse execute(AuroraRequest<V> request) throws IOException {
        AuroraResponse auroraResponse = doExecute(request);
        triggerAfter(request, auroraResponse);
        return auroraResponse;
    }

    protected abstract <V> AuroraResponse doExecute(AuroraRequest<V> request) throws IOException;

    protected <V> AuroraResponse triggerAfter(AuroraRequest<V> request, AuroraResponse auroraResponse) {
        configuration.interceptorChain().after(request, auroraResponse);
        return auroraResponse;
    }

    protected <V> V resultHandle(AuroraResponse auroraResponse, Class<V> resultType) {
        return configuration.getResultHandler().handle(auroraResponse, resultType);
    }
}