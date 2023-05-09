package top.javap.aurora.interceptor;

import top.javap.aurora.domain.HttpResponse;
import top.javap.aurora.invoke.Invocation;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/12
 **/
public class InterceptorChain implements AuroraInterceptor {

    private final List<AuroraInterceptor> interceptors = new CopyOnWriteArrayList<>();

    public InterceptorChain() {
    }

    public InterceptorChain(Collection<AuroraInterceptor> interceptors) {
        this.interceptors.addAll(interceptors);
    }

    public void addInterceptor(AuroraInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    public void addInterceptor(Collection<AuroraInterceptor> interceptors) {
        this.interceptors.addAll(interceptors);
    }

    @Override
    public <V> boolean before(Invocation invocation) {
        for (AuroraInterceptor interceptor : interceptors) {
            if (!interceptor.before(invocation)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public <V> void after(Invocation invocation, HttpResponse response) {
        for (AuroraInterceptor interceptor : interceptors) {
            interceptor.after(invocation, response);
        }
    }
}