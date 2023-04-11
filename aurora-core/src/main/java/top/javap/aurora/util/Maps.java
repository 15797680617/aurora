package top.javap.aurora.util;

import java.util.Map;
import java.util.Objects;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/7
 **/
public final class Maps {

    public static boolean notEmpty(Map map) {
        return Objects.nonNull(map) && !map.isEmpty();
    }
}