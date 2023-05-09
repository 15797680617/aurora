package top.javap.aurora.invoke;

import top.javap.aurora.domain.HttpResponse;
import top.javap.aurora.domain.Result;
import top.javap.aurora.enums.InvokeMode;
import top.javap.aurora.interceptor.InterceptorChain;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/8
 **/
public abstract class BaseInvoker implements Invoker {

    @Override
    public final Result invoke(Invocation invocation) {
        InterceptorChain chain = getInterceptorChain(invocation);
        if (chain.before(invocation)) {
            return doInvoke(invocation);
        }
        return null;
    }

    private Result doInvoke(Invocation invocation) {
        InvokeMode invokeMode = invocation.getInvokeMode();
        if (InvokeMode.SYNC.equals(invokeMode)) {
            return doSync(invocation);
        } else if (InvokeMode.FUTURE.equals(invokeMode)) {
            return doFuture(invocation);
        } else if (InvokeMode.CALLBACK.equals(invokeMode)) {
            doCallback(invocation);
        }
        return null;
    }

    protected abstract Result doSync(Invocation invocation);

    protected abstract Result doFuture(Invocation invocation);

    protected abstract void doCallback(Invocation invocation);

    protected final InterceptorChain getInterceptorChain(Invocation invocation) {
        return invocation.getMethodMetadata().getMapper().getConfiguration().interceptorChain();
    }

    protected final HttpResponse triggerAfter(Invocation invocation, HttpResponse response) {
        getInterceptorChain(invocation).after(invocation, response);
        return response;
    }
}