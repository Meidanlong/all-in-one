package com.mdl.springboot.aigc.service.openai;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.proxy.ProxyServer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ChatGptServiceImpl {

    public static void main(String[] args) throws Exception {
        //原始地址
        String apiUrl = "https://api.openai.com/v1/chat/completions";
        String apiKey = "";
        List<JSONObject> msgData = new ArrayList<>();
        msgData.add(
                new JSONObject()
                        .fluentPut("role", "user")
                        .fluentPut("content", "500字夸奖我一下")
        );
        JSONObject req = new JSONObject()
                // 完成时要生成的最大token数量。
                // 提示的token计数加上max_tokens不能超过模型的上下文长度。大多数模型的上下文长度为2048个token（最新模型除外，支持4096个）。
                .fluentPut("max_tokens", 512)
                .fluentPut("model", "gpt-3.5-turbo")
//                .fluentPut("model", "gpt-4")
                // 可选 默认值1
                // 使用什么样的采样温度，介于0和2之间。较高的值（如0.8）将使输出更加随机，而较低的值（例如0.2）将使其更加集中和确定。
                // 通常建议更改它或top_p，但不能同时更改两者。
                .fluentPut("temperature", 0.6)
                //告诉ChatGpt 使用流式返回，传FALSE 则返回完整的JSON（有点慢）
                .fluentPut("stream", Boolean.TRUE)
                .fluentPut("messages", msgData);

        log.info("》》 {}", JSON.toJSONString(req));
        AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
        asyncHttpClient.preparePost(apiUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Accept", "text/event-stream")
                .setBody(JSON.toJSONString(req))
                .execute(new ChatGptStreamedHandler())
                .get();
        asyncHttpClient.close();
    }

}
