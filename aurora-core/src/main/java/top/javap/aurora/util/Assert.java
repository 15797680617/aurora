package top.javap.aurora.util;

import top.javap.aurora.exception.AuroraException;

import java.util.Objects;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/7
 **/
public final class Assert {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new AuroraException(message);
        }
    }

    public static void notNull(Object obj, String message) {
        if (Objects.isNull(obj)) {
            throw new AuroraException(message);
        }
    }

    public static void notEmpty(Object[] arr, String message) {
        if (Objects.isNull(arr) || arr.length <= 0) {
            throw new AuroraException(message);
        }
    }
}