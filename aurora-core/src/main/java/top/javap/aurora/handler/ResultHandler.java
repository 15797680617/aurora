package top.javap.aurora.handler;

import top.javap.aurora.domain.HttpResponse;

/**
 * 结果处理
 *
 * @Author: pch
 * @Date: 2023/4/11 11:28
 * @Description:
 */
public interface ResultHandler {

    <T> T handle(HttpResponse response, Class<T> resultType);

}