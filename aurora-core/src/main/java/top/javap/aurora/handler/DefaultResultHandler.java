package top.javap.aurora.handler;

import com.alibaba.fastjson.JSON;
import top.javap.aurora.convert.Converter;
import top.javap.aurora.convert.ConverterFactory;
import top.javap.aurora.domain.HttpResponse;
import top.javap.aurora.exception.AuroraException;

import java.util.Objects;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/11
 **/
public class DefaultResultHandler implements ResultHandler {

    private final ConverterFactory converterFactory;

    public DefaultResultHandler(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

    @Override
    public <T> T handle(HttpResponse response, Class<T> resultType) {
        if (Objects.isNull(response) || Objects.isNull(resultType) || resultType.isAssignableFrom(Void.class) || resultType.isAssignableFrom(void.class)) {
            return null;
        }
        if (HttpResponse.class.isAssignableFrom(resultType)) {
            return (T) response;
        }
        Converter<String, T> converter = converterFactory.getConverter(String.class, resultType);
        if (Objects.nonNull(converter)) {
            return converter.convert(response.getBody());
        }
        try {
            return JSON.parseObject(response.getBody(), resultType);
        } catch (Exception e) {
            throw new AuroraException("No converter available:" + resultType, e);
        }
    }
}