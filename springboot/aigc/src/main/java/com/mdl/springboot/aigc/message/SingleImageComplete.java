package com.mdl.springboot.aigc.message;

import com.mdl.common.executor.BizExecutor;
import com.mdl.springboot.aigc.AigcApplication;
import com.mdl.springboot.aigc.utils.MidjourneyUtil;
import lombok.extern.slf4j.Slf4j;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageType;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Slf4j
@Component
public class SingleImageComplete implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        try{
            MessageAuthor messageAuthor = event.getMessageAuthor();
            if(messageAuthor.isBotUser()){
                Message message = event.getMessage();
                if(MessageType.REPLY.equals(message.getType())) {
                    String prompt = MidjourneyUtil.getReturnPrompt(message.getContent(), true);
                    // 获取图片详情
                    MessageAttachment image = event.getMessage().getAttachments().get(0);
                    String discordImageUrl = image.getUrl().toString();
                    SingleImageComplete self = AigcApplication.getBean(this);
                    BizExecutor.getInstance().getThreadPool().execute(() -> self.uploadImageAndSendMsg(message.getId(), prompt, discordImageUrl));
                }
            }
        }catch (NoSuchElementException e) {
            log.info("!==[SingleImageComplete#onMessageCreate] no such element: " + e.getMessage(), e);
            e.printStackTrace();
        }catch (Exception e) {
            log.info("!==[SingleImageComplete#onMessageCreate] ex is " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 获取单图并转换为可用链接（略）
     * @param messageId
     * @param prompt
     * @param discordImageUrl
     * @return
     */
    public String uploadImageAndSendMsg(Long messageId, String prompt, String discordImageUrl) {
        return discordImageUrl;
    }
}
