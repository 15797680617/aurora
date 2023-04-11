package top.javap.aurora.executor;

import top.javap.aurora.exception.AuroraException;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/10
 **/
public abstract class Callback<V> {

    public void onSuccess(V result) {

    }

    public void onError(AuroraException exception) {

    }
}