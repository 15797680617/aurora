package top.javap.aurora.convert;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/4
 **/
public class LongConverter extends StringConverter<Long> {

    @Override
    public Long doConvert(String source) {
        return Long.valueOf(source);
    }
}