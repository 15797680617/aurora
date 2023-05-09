package top.javap.aurora.reflection;

import top.javap.aurora.domain.Mapper;

import java.lang.reflect.Method;

/**
 * 通过asm读取方法元数据
 *
 * @author: pch
 * @description:
 * @date: 2023/5/6
 **/
public class ASMMethodMetadataReader extends BaseMethodMetadataReader {

    @Override
    protected MethodMetadata createMethodMetadata(Mapper mapper, Method method) {
        // todo
        return null;
    }
}