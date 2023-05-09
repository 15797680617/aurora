package top.javap.aurora.reflection;

import top.javap.aurora.domain.Mapper;

import java.lang.reflect.Method;

/**
 * @Author: pch
 * @Date: 2023/5/6 16:24
 * @Description:
 */
public interface MethodMetadataReader {

    MethodMetadata getMethodMetadata(Mapper mapper, Method method);

}