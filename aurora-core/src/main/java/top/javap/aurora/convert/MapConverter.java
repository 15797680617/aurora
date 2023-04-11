package top.javap.aurora.convert;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/4
 **/
public class MapConverter extends StringConverter<Map> {

    @Override
    public Map doConvert(String source) {
        return JSON.parseObject(source, HashMap.class);
    }
}