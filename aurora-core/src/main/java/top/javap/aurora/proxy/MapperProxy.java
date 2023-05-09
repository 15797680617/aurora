package top.javap.aurora.proxy;

import top.javap.aurora.domain.Mapper;
import top.javap.aurora.domain.Result;
import top.javap.aurora.enums.InvokeMode;
import top.javap.aurora.exception.AuroraException;
import top.javap.aurora.invoke.Invocation;
import top.javap.aurora.invoke.Invoker;
import top.javap.aurora.invoke.SimpleInvocation;
import top.javap.aurora.reflection.DefaultMethodLookup;
import top.javap.aurora.reflection.MethodMetadata;
import top.javap.aurora.util.MethodUtil;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/7
 **/
public class MapperProxy<T> implements InvocationHandler {

    private final Mapper<T> mapper;
    private final Invoker invoker;
    private final MethodHandles.Lookup methodLookup;

    public MapperProxy(Mapper<T> mapper) {
        this.mapper = mapper;
        this.invoker = mapper.getConfiguration().getInvoker();
        methodLookup = DefaultMethodLookup.lookup(mapper.getTargetInterface());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isDefault()) {
            return invokeDefaultMethod(proxy, method, args);
        }
        if (MethodUtil.declareFromObject(method)) {
            return method.invoke(this, args);
        }
        return doInvoke(method, args);
    }

    private Object doInvoke(Method method, Object[] args) {
        final MethodMetadata methodMetadata = getMethodMetadata(method);
        final Invocation invocation = buildInvocation(methodMetadata, args);
        Result result = invoker.invoke(invocation);
        if (Objects.nonNull(result)) {
            if (result.hasException()) {
                throw new AuroraException("onerror:", result.getException());
            }
            if (InvokeMode.SYNC.equals(invocation.getInvokeMode())) {
                return mapper.getConfiguration().getResultHandler().handle(result.getResponse(), invocation.getResultType());
            } else if (InvokeMode.FUTURE.equals(invocation.getInvokeMode())) {
                return result.getFuture();
            }
        }
        // others or callback only needs to return null
        return null;
    }

    private MethodMetadata getMethodMetadata(Method method) {
        return mapper.getConfiguration().getMethodMetadataReader().getMethodMetadata(mapper, method);
    }

    private Invocation buildInvocation(MethodMetadata methodMetadata, Object[] args) {
        return new SimpleInvocation(methodMetadata, args);
    }

    private Object invokeDefaultMethod(Object proxy, Method method, Object[] args) {
        try {
            MethodHandle methodHandle = methodLookup.findSpecial(mapper.getTargetInterface(), method.getName(), MethodType.methodType(method.getReturnType(), method.getParameterTypes()), mapper.getTargetInterface());
            return methodHandle.bindTo(proxy).invokeWithArguments(args);
        } catch (Throwable e) {
            throw new AuroraException("The default method fails to execute", e);
        }
    }
}