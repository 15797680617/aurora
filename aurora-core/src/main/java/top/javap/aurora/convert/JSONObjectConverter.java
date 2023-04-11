package top.javap.aurora.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/4
 **/
public class JSONObjectConverter extends StringConverter<JSONObject> {

    @Override
    public JSONObject doConvert(String source) {
        return JSON.parseObject(source);
    }
}