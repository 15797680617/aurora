package top.javap.aurora.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/4
 **/
public class JSONOArrayConverter extends StringConverter<JSONArray> {
    @Override
    public JSONArray doConvert(String source) {
        return JSON.parseArray(source);
    }
}