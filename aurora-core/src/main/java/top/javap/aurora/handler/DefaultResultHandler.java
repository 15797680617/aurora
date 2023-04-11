package top.javap.aurora.handler;

import com.alibaba.fastjson.JSON;
import top.javap.aurora.config.AuroraConfiguration;
import top.javap.aurora.convert.Converter;
import top.javap.aurora.domain.HttpResponse;
import top.javap.aurora.exception.AuroraException;

import java.util.Objects;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/11
 **/
public class DefaultResultHandler implements ResultHandler {

    private final AuroraConfiguration auroraConfiguration;

    public DefaultResultHandler(AuroraConfiguration auroraConfiguration) {
        this.auroraConfiguration = auroraConfiguration;
    }

    @Override
    public <T> T handle(HttpResponse response, Class<T> resultType) {
        if (Objects.isNull(response) || Objects.isNull(resultType) || resultType.isAssignableFrom(Void.class) || resultType.isAssignableFrom(void.class)) {
            return null;
        }
        if (resultType.isAssignableFrom(HttpResponse.class)) {
            return (T) response;
        }
        Converter<String, T> converter = auroraConfiguration.getConverterFactory().getConverter(String.class, resultType);
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