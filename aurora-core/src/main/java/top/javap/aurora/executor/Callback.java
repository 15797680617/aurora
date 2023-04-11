package top.javap.aurora.executor;

import top.javap.aurora.exception.AuroraException;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/10
 **/
public abstract class Callback<T> {

    public void onSuccess(T result) {

    }

    public void onError(AuroraException exception) {

    }
}