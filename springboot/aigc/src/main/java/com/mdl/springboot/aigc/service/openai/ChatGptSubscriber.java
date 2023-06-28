package com.mdl.springboot.aigc.service.openai;

import com.alibaba.fastjson.JSON;
import com.mdl.common.utils.StringUtil;
import com.mdl.springboot.aigc.domain.openai.ChatCompletionResponseDTO;
import com.mdl.springboot.aigc.utils.OpenAiUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

/**
 * 订阅者
 *
 * @Description 订阅者
 * @Author Mr.dan
 * @Date 2023-05-04 13:18
 */
@Slf4j
public class ChatGptSubscriber implements Subscriber {

    private final static String DATA_SEPARATOR = "data: ";
    private final static String FINISH_MARK = "[DONE]";

    @Override
    public void onSubscribe(Subscription s) {
        log.info(">>>>> onSubscribe  {}", JSON.toJSONString(s));
        s.request(Long.MAX_VALUE);
    }

    // 暂存字段
    private String tempStorage = Strings.EMPTY;

    @Override
    public void onNext(Object o) {
        // 这里就是监听到的响应
        // {
        //  "bodyByteBuffer": {
        //    "array": "eyJlcnJvcl9jb2RlIjoxMDAsImVycm9yX21zZyI6IkludmFsaWQgcGFyYW1ldGVyIn0=",
        //    "limit": 50,
        //    "position": 0
        //  },
        //  "bodyPartBytes": "eyJlcnJvcl9jb2RlIjoxMDAsImVycm9yX21zZyI6IkludmFsaWQgcGFyYW1ldGVyIn0=",
        //  "last": false
        //}
        // GPT返回
        // {
        //  "id": "chatcmpl-7WHp5USDDqtzkqJd4EYj97NlmC6DO",
        //  "object": "chat.completion.chunk",
        //  "created": 1687929363,
        //  "model": "gpt-3.5-turbo-0613",
        //  "choices": [
        //    {
        //      "index": 0,
        //      "delta": {
        //        "content": "你"
        //      },
        //      "finish_reason": null
        //    }
        //  ]
        String bodyDatas = new String(JSON.parseObject(JSON.toJSONString(o)).getBytes("bodyPartBytes"));
        log.info(">>>>> onNext  {}", bodyDatas);
        boolean firstLine = false;
        StringBuilder words = new StringBuilder();
        if(bodyDatas.startsWith(DATA_SEPARATOR)){
            firstLine = true;
            // 结尾刚好为完整JSON
            if(StringUtil.isNotEmpty(tempStorage)){
                // 通用处理
                words.append(getWord(tempStorage));
            }
        }
        String[] singleWords = bodyDatas.split(DATA_SEPARATOR);

        for(int i=0; i<singleWords.length; i++){
            String singleWordJsonStr = singleWords[i];
            // 校验
            if(StringUtil.isEmpty(singleWordJsonStr)){
                continue;
            }
            if(FINISH_MARK.equals(singleWordJsonStr)){
                break;
            }
            // 结尾处理
            if(i == singleWords.length -1){
                tempStorage = singleWordJsonStr;
                break;
            }
            // 开头处理
            if(!firstLine && i==0 && StringUtil.isNotEmpty(tempStorage)){
                singleWordJsonStr = tempStorage + singleWordJsonStr;
            }
            // 通用处理
            String word = getWord(singleWordJsonStr);
            if(StringUtil.isNotEmpty(word)){
                words.append(word);
            }
        }

        log.info(">>>>> ai says: {}", words);
    }

    private String getWord(String singleWordJsonStr) {
        ChatCompletionResponseDTO chatCompletionResponseDTO = OpenAiUtil.checkAndGetResponse(singleWordJsonStr, ChatCompletionResponseDTO.class);
        return chatCompletionResponseDTO.getChoices().get(0).getDelta().getContent();
    }

    @Override
    public void onError(Throwable t) {
        //这里就是监听到的响应
        log.info(">>>>> onError  {}", JSON.toJSONString(t));
    }

    @Override
    public void onComplete() {
        log.info(">>>>> onComplete ");
    }
}


