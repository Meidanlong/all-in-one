package com.mdl.springboot.aigc.service.huoshan.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mdl.springboot.aigc.service.huoshan.IVolcChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

/**
 * 火山对话服务实现
 *
 * @author meidanlong
 * @date 2025年07月24日
 * @version: 1.0
 */
@Slf4j
@Service
public class VolcChatServiceImpl implements IVolcChatService {

    private static final String API_URL = "https://ark.cn-beijing.volces.com/api/v3/chat/completions";
    private static final String BEARER_TOKEN = "d5207302-4bd3-443f-8702-a9da91ec7ea9";

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 提取人物特征（包含地区信息）
     */
    public JSONObject extractPersonFeature(String imageUrl) {
        JSONObject request = buildPersonFeatureRequest(imageUrl);
        String response = sendRequest(request);
        return parsePersonFeatureResponse(response);
    }

    /**
     * 构建人物特征提取请求
     */
    private JSONObject buildPersonFeatureRequest(String imageUrl) {
        JSONObject request = new JSONObject();
        request.put("model", "doubao-1-5-thinking-vision-pro-250428");
        request.put("messages", buildMessages(imageUrl));
        request.put("response_format", buildResponseFormat());
        request.put("thinking", buildThinkingConfig());

        return request;
    }

    /**
     * 构建消息内容
     */
    private JSONArray buildMessages(String imageUrl) {
        JSONArray messages = new JSONArray();
        JSONObject message = new JSONObject();
        message.put("role", "user");
        message.put("content", buildImageContent(imageUrl));
        messages.add(message);

        return messages;
    }

    /**
     * 构建图片内容
     */
    private JSONArray buildImageContent(String imageUrl) {
        JSONArray content = new JSONArray();
        JSONObject imageContent = new JSONObject();
        imageContent.put("type", "image_url");

        JSONObject imageUrlObj = new JSONObject();
        imageUrlObj.put("url", imageUrl);
        imageUrlObj.put("detail", "high");
        imageContent.put("image_url", imageUrlObj);

        content.add(imageContent);
        return content;
    }

    /**
     * 构建响应格式配置
     */
    private JSONObject buildResponseFormat() {
        JSONObject responseFormat = new JSONObject();
        responseFormat.put("type", "json_schema");
        responseFormat.put("json_schema", buildJsonSchema());

        return responseFormat;
    }

    /**
     * 构建JSON Schema配置
     */
    private JSONObject buildJsonSchema() {
        JSONObject jsonSchema = new JSONObject();
        jsonSchema.put("name", "person_feature");
        jsonSchema.put("description", "图片中人物的特征提取结果");
        jsonSchema.put("schema", buildSchema());
        jsonSchema.put("strict", true);

        return jsonSchema;
    }

    /**
     * 构建Schema结构
     */
    private JSONObject buildSchema() {
        JSONObject schema = new JSONObject();
        schema.put("type", "object");
        schema.put("properties", buildSchemaProperties());
        schema.put("required", buildRequiredFields());

        return schema;
    }

    /**
     * 构建Schema属性
     */
    private JSONObject buildSchemaProperties() {
        JSONObject properties = new JSONObject();

        properties.put("region", createProperty("string", "什么地区的人，如Middle Eastern等"));
        properties.put("gender", createProperty("string", "性别，男为boy，女为girl"));
        properties.put("skinColor", createProperty("string", "肤色描述，如rich skin tone等"));
        properties.put("eyesStyle", createProperty("string", "眼睛描述，如warm brown eyes等"));
        properties.put("hairStyle", createProperty("string", "发型描述，如Curly dark hair等"));

        return properties;
    }

    /**
     * 创建属性对象
     */
    private JSONObject createProperty(String type, String description) {
        JSONObject property = new JSONObject();
        property.put("type", type);
        property.put("description", description);
        return property;
    }

    /**
     * 构建必填字段列表
     */
    private JSONArray buildRequiredFields() {
        JSONArray required = new JSONArray();
        required.add("region");
        required.add("gender");
        required.add("skinColor");
        required.add("eyesStyle");
        required.add("hairStyle");

        return required;
    }

    /**
     * 构建思考模式配置
     */
    private JSONObject buildThinkingConfig() {
        JSONObject thinking = new JSONObject();
        thinking.put("type", "enabled");
        return thinking;
    }

    /**
     * 解析人物特征响应
     */
    private JSONObject parsePersonFeatureResponse(String response) {
        try {
            JSONObject responseJson = JSON.parseObject(response);
            JSONArray choices = responseJson.getJSONArray("choices");

            if (choices != null && !choices.isEmpty()) {
                JSONObject firstChoice = choices.getJSONObject(0);
                JSONObject message = firstChoice.getJSONObject("message");

                if (message != null) {
                    String content = message.getString("content");
                    if (content != null && !content.trim().isEmpty()) {
                        // 尝试解析content中的JSON
                        return JSON.parseObject(content);
                    }
                }
            }

            log.warn("无法从响应中解析出有效的人物特征信息");
            return new JSONObject();

        } catch (Exception e) {
            log.error("解析人物特征响应失败", e);
            return new JSONObject();
        }
    }

    /**
     * 发送请求到火山API
     */
    private String sendRequest(JSONObject request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(BEARER_TOKEN);

            HttpEntity<String> entity = new HttpEntity<>(request.toJSONString(), headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    API_URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            log.info("API Response: {}", response.getBody());
            return response.getBody();

        } catch (Exception e) {
            log.error("调用火山API失败", e);
            throw new RuntimeException("调用火山API失败", e);
        }
    }

    public static void main(String[] args) {
        StopWatch sw = new StopWatch();
        sw.start("识别图像人物特征");
        VolcChatServiceImpl volcChatService = new VolcChatServiceImpl();
        String imgUrl = "https://p0.beebo.media/beebo_edu/public/image/3c6fe12c5c1e1b5ba40b9ad8bed12868c6f73.jpg";
        JSONObject personFeature = volcChatService.extractPersonFeature(imgUrl);
        System.out.println(JSON.toJSONString(personFeature));
        sw.stop();
        System.out.println(sw.prettyPrint());
    }
}