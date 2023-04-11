package top.javap.aurora.convert;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/4
 **/
public class DoubleConverter extends StringConverter<Double> {

    @Override
    public Double doConvert(String source) {
        return Double.valueOf(source);
    }
}