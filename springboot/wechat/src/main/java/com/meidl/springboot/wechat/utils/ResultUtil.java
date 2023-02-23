package com.meidl.springboot.wechat.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mdl.common.domain.BusinessException;
import com.mdl.common.enums.ErrorEnum;
import com.mdl.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/2/16 19:14
 */
@Slf4j
public class ResultUtil {

    private final static String exMsg = "%s异常";
    private final static String errorMsg = "%s失败";


    public static JSONObject getResult(String result, String desc){
        if(StringUtil.isEmpty(result)){
            String msg = String.format(exMsg, desc);
            log.error("{}, result is empty", msg);
            throw new BusinessException(ErrorEnum.XXX, msg);
        }
        JSONObject jsonObject = JSON.parseObject(result);
        Integer errcode = jsonObject.getInteger("errcode");
        if(errcode == null || errcode != 0){
            String msg = String.format(errorMsg, desc);
            log.error("{}, result={}", msg, result);
            throw new BusinessException(ErrorEnum.XXX, msg);
        }
        log.info("{} - {}", desc, jsonObject.toJSONString());
        return jsonObject;
    }
}
