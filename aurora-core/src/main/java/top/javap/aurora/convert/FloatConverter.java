package top.javap.aurora.convert;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/4
 **/
public class FloatConverter extends StringConverter<Float> {

    @Override
    public Float doConvert(String source) {
        return Float.valueOf(source);
    }
}