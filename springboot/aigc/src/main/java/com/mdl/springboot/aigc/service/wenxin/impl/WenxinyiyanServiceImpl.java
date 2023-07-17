package com.mdl.springboot.aigc.service.wenxin.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mdl.springboot.aigc.service.openai.ChatGptStreamedHandler;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.proxy.ProxyServer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *  文心一言服务
 *
 *  {@link <a href=""/a>}
 * @author meidanlong
 * @date 2023/6/26
 * @version 1.0.0
 */
@Slf4j
public class WenxinyiyanServiceImpl {

    public static void main(String[] args) throws Exception {
        //原始地址
        String apiUrl = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant?access_token=%s";
        String accessToken = "";
        apiUrl = String.format(apiUrl, accessToken);
        List<JSONObject> msgData = new ArrayList<>();
        msgData.add(
                new JSONObject()
                        .fluentPut("role", "user")
                        .fluentPut("content", "夸奖我一下")
        );
        JSONObject req = new JSONObject()
                //告诉ChatGpt 使用流式返回，传FALSE 则返回完整的JSON（有点慢）
                .fluentPut("stream", Boolean.TRUE)
                .fluentPut("messages", msgData);

        log.info("》》 {}", JSON.toJSONString(req));
        AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
        asyncHttpClient.preparePost(apiUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "text/event-stream")
                .setBody(JSON.toJSONString(req))
                .execute(new ChatGptStreamedHandler())
                .get();
        asyncHttpClient.close();
    }
}
