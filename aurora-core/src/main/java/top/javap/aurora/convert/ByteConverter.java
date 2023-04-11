package top.javap.aurora.convert;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/4
 **/
public class ByteConverter extends StringConverter<Byte> {

    @Override
    public Byte doConvert(String source) {
        return Byte.valueOf(source);
    }
}