package top.javap.aurora.convert;

/**
 * @Author: pch
 * @Date: 2023/4/4 19:55
 * @Description:
 */
public interface Converter<S, T> {

    T convert(S source);
}