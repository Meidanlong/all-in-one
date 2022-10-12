package com.mdl.springboot.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/10/9 18:53
 */
public class JsonUtil {

    public static String toJson(Object obj){
        return JSON.toJSONString(obj);
    }

    public static String toPrettyJson(Object obj){
        return JSON.toJSONString(obj, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
    }
}
