package top.javap.aurora.invoke;

import top.javap.aurora.domain.Result;

/**
 * @Author: pch
 * @Date: 2023/5/6 15:24
 * @Description:
 */
public interface Invoker {

    Result invoke(Invocation invocation);

}