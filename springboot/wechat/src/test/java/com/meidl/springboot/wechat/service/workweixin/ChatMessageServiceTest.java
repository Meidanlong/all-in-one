package com.meidl.springboot.wechat.service.workweixin;

import com.meidl.springboot.wechat.WechatApplication;
import com.meidl.springboot.wechat.domain.dto.ChatMessageDTO;
import com.meidl.springboot.wechat.service.workweixin.crop.ChatMessageService;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description: 
 * @author: meidanlong
 * @date: 2023/3/7 10:04
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WechatApplication.class)
public class ChatMessageServiceTest extends TestCase {

    @Resource
    private ChatMessageService chatMessageService;

    @Test
    public void testSendChatMessage() {
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
        chatMessageDTO.setChatid("firstChatGroup");
        chatMessageDTO.setTextContent("这是内部自创群聊的第一条消息");
        chatMessageDTO.setMsgtype("text");
        chatMessageService.sendChatMessage(chatMessageDTO);
    }
}