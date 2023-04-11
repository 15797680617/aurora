package top.javap.aurora.convert;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/4
 **/
public class IntegerConverter extends StringConverter<Integer> {

    @Override
    public Integer doConvert(String source) {
        return Integer.valueOf(source);
    }
}