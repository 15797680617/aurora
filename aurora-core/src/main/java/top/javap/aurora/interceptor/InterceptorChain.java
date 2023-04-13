package top.javap.aurora.interceptor;

import top.javap.aurora.domain.AuroraRequest;
import top.javap.aurora.domain.AuroraResponse;
import top.javap.aurora.reflection.AuroraMethod;

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
    public <V> boolean before(AuroraMethod<V> method, AuroraRequest<V> request, Object[] args) {
        for (AuroraInterceptor interceptor : interceptors) {
            if (!interceptor.before(method, request, args)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public <V> void after(AuroraRequest<V> request, AuroraResponse response) {
        for (AuroraInterceptor interceptor : interceptors) {
            interceptor.after(request, response);
        }
    }
}