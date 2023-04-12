package top.javap.aurora.domain;

import top.javap.aurora.config.AuroraConfiguration;
import top.javap.aurora.proxy.MapperProxy;
import top.javap.aurora.util.MapperClassUtil;

import java.lang.reflect.Proxy;

/**
 * @author: pch
 * @description:
 * @date: 2023/3/23
 **/
public class Mapper<T> {

    private final Class<T> targetInterface;
    private final AuroraConfiguration configuration;

    public Mapper(Class<T> targetInterface, AuroraConfiguration configuration) {
        MapperClassUtil.checkInterface(targetInterface);
        this.targetInterface = targetInterface;
        this.configuration = configuration;
    }

    public Class<T> getTargetInterface() {
        return targetInterface;
    }

    public AuroraConfiguration getConfiguration() {
        return configuration;
    }

    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(Mapper.class.getClassLoader(), new Class[]{targetInterface}, new MapperProxy(this));
    }
}