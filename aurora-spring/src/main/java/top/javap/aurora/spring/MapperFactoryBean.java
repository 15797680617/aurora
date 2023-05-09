package top.javap.aurora.spring;

import org.springframework.beans.factory.FactoryBean;
import top.javap.aurora.Aurora;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/12
 **/
public class MapperFactoryBean<T> implements FactoryBean {

    private final Class<T> mapperInterface;

    public MapperFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object getObject() throws Exception {
        return Aurora.getMapper(mapperInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }
}