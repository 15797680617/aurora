package top.javap.aurora.convert;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/4
 **/
public class ShortConverter extends StringConverter<Short> {

    @Override
    public Short doConvert(String source) {
        return Short.valueOf(source);
    }
}