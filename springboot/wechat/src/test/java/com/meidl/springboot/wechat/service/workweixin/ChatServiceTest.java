package com.meidl.springboot.wechat.service.workweixin;

import com.meidl.springboot.wechat.WechatApplication;
import com.meidl.springboot.wechat.domain.dto.ChatDTO;
import com.meidl.springboot.wechat.service.workweixin.crop.ChatService;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/3/7 09:40
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WechatApplication.class)
public class ChatServiceTest extends TestCase {

    @Resource
    private ChatService chatService;

    @Test
    public void testCreateChat() {
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setName("自动建群1群");
        chatDTO.setOwner("meidanlong");
        chatDTO.setUserlist(Arrays.asList("meidanlong", "luzelong"));
        chatDTO.setChatid("firstChatGroup");
        String chat = chatService.createChat(chatDTO);
    }
}