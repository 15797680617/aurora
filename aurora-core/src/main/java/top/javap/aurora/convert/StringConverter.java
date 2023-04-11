package top.javap.aurora.convert;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/4
 **/
public abstract class StringConverter<T> implements Converter<String, T> {

    @Override
    public T convert(String source) {
        try {
            return doConvert(source);
        } catch (Exception e) {
            throw e;
        }
    }

    protected abstract T doConvert(String source);
}