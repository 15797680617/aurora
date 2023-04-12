package top.javap.aurora.util;

import top.javap.aurora.annotation.Mapper;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/7
 **/
public final class MapperClassUtil {

    public static void checkInterface(Class<?> mapperClass) {
        Assert.isTrue(mapperClass.isInterface(), mapperClass + " Must be an interface");
        Assert.isTrue(mapperClass.isAnnotationPresent(Mapper.class), mapperClass + " no @Mapper annotation");
    }
}