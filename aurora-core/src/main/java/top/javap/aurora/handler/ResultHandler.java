package top.javap.aurora.handler;

import top.javap.aurora.domain.HttpResponse;

/**
 * @Author: pch
 * @Date: 2023/4/11 11:28
 * @Description:
 */
public interface ResultHandler {

    <T> T handle(HttpResponse response, Class<T> resultType);

}