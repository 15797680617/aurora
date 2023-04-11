package top.javap.aurora.reflection;

import top.javap.aurora.domain.Mapper;

import java.lang.reflect.Method;

/**
 * @Author: pch
 * @Date: 2023/4/7 15:26
 * @Description:
 */
public interface AuroraMethodParser {

    AuroraMethod parse(Mapper mapper, Method method);

}