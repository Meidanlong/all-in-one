package com.mdl.springboot.aigc.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mdl.common.domain.BusinessException;
import com.mdl.common.utils.StringUtil;
import com.mdl.springboot.aigc.domain.openai.CompletionError;
import org.apache.http.HttpException;

public class OpenAiUtil {

    /**
     * 校验并根据响应字符串获取CompletionResponseDTO对象
     * 不满足预期的响应统一抛异常，后续可以根据异常再次重试请求
     * @param response
     * @return
     * @throws HttpException
     */
    public static  <T> T checkAndGetResponse(String response, Class<T> clazz) {
        //1.http响应字符串为空 ==> 无意义的空响应
        if (StringUtil.isEmpty(response)) {
            throw new BusinessException("openai返回的是无意义的空响应!");
        }
        JSONObject parseObject = JSON.parseObject(response);
        //2.openai对文字提问没任何回答，不符合预期
        if (parseObject.get("choices") == null || parseObject.getJSONArray("choices").size() == 0){
            throw new BusinessException("openai回答列表为空，不符合预期！");
        }
        //3.响应中有error，这种情况是说明调用参数有问题，请检查调用的参数
        if (parseObject.get("error") != null) {
            CompletionError error = parseObject.getJSONObject("error").toJavaObject(CompletionError.class);
            throw new BusinessException(error.getMessage());
        }
        return parseObject.toJavaObject(clazz);
    }
}
