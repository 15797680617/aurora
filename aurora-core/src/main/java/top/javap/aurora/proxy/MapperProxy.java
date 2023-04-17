package top.javap.aurora.proxy;

import top.javap.aurora.domain.Mapper;
import top.javap.aurora.exception.AuroraException;
import top.javap.aurora.reflection.AuroraMethodFactory;
import top.javap.aurora.reflection.DefaultMethodLookup;
import top.javap.aurora.util.MethodUtil;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/7
 **/
public class MapperProxy<T> implements InvocationHandler {

    private final Mapper<T> mapper;
    private final MethodHandles.Lookup methodLookup;

    public MapperProxy(Mapper<T> mapper) {
        this.mapper = mapper;
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
        return AuroraMethodFactory.getAuroraMethod(mapper, method).invoke(args);
    }

    private Object invokeDefaultMethod(Object proxy, Method method, Object[] args) {
        try {
            MethodHandle methodHandle = methodLookup.findSpecial(mapper.getTargetInterface(), method.getName(),
                    MethodType.methodType(method.getReturnType(), method.getParameterTypes()), mapper.getTargetInterface());
            return methodHandle.bindTo(proxy).invokeWithArguments(args);
        } catch (Throwable e) {
            throw new AuroraException("The default method fails to execute", e);
        }
    }
}