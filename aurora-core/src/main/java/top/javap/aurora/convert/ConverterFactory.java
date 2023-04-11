package top.javap.aurora.convert;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/4
 **/
public final class ConverterFactory {
    private final Map<String, Converter> CONVERTER_MAP;

    public ConverterFactory() {
        CONVERTER_MAP = new HashMap<>();
        CONVERTER_MAP.put(buildKey(String.class, JSONObject.class), new JSONObjectConverter());
        CONVERTER_MAP.put(buildKey(String.class, Map.class), new MapConverter());
        CONVERTER_MAP.put(buildKey(String.class, String.class), new SSConverter());
        CONVERTER_MAP.put(buildKey(String.class, byte.class), new ByteConverter());
        CONVERTER_MAP.put(buildKey(String.class, Byte.class), new ByteConverter());
        CONVERTER_MAP.put(buildKey(String.class, short.class), new ShortConverter());
        CONVERTER_MAP.put(buildKey(String.class, Short.class), new ShortConverter());
        CONVERTER_MAP.put(buildKey(String.class, int.class), new IntegerConverter());
        CONVERTER_MAP.put(buildKey(String.class, Integer.class), new IntegerConverter());
        CONVERTER_MAP.put(buildKey(String.class, float.class), new FloatConverter());
        CONVERTER_MAP.put(buildKey(String.class, Float.class), new FloatConverter());
        CONVERTER_MAP.put(buildKey(String.class, double.class), new DoubleConverter());
        CONVERTER_MAP.put(buildKey(String.class, Double.class), new DoubleConverter());
    }

    public <S, T> Converter<S, T> getConverter(Class<S> sourceClass, Class<T> targetClass) {
        return CONVERTER_MAP.get(buildKey(sourceClass, targetClass));
    }

    private <S, T> String buildKey(Class<S> sourceClass, Class<T> targetClass) {
        return sourceClass.getName() + targetClass.getName();
    }
}