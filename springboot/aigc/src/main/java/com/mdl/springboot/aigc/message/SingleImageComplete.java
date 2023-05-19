package com.mdl.springboot.aigc.message;

import com.mdl.springboot.aigc.AigcApplication;
import lombok.extern.slf4j.Slf4j;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageType;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SingleImageComplete implements MessageCreateListener {

    private static final String MJ_ROBOT_NAME = "Midjourney Bot";
    private static final Long MJ_ROBOT_ID = 936929561302675456L;
//    Configuration configuration = new Configuration();

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        SingleImageComplete self = AigcApplication.getBean(this);
        self.eventSolution(event);
    }

    @Async
    public void eventSolution(MessageCreateEvent event) {
        try{
            TextChannel channel = event.getChannel();
            long channelId = channel.getId();
            log.info("===[SingleImageComplete#eventSolution] channelId is " + channelId);
            MessageAuthor messageAuthor = event.getMessageAuthor();
            if(messageAuthor.isBotUser()){
                log.info("===[SingleImageComplete#eventSolution] isBotUser");
                String robName = messageAuthor.asUser().orElse(null).getName();
                log.info("===[SingleImageComplete#eventSolution] robName is " + robName);
                long messageAuthorId = messageAuthor.getId();
                log.info("===[SingleImageComplete#eventSolution] messageAuthorId is " + messageAuthorId);
                Message message = event.getMessage();
                long messageId = message.getId();
                log.info("===[SingleImageComplete#eventSolution] messageId is " + messageId);
                if(MessageType.REPLY.equals(message.getType())){
                    String messageContent = message.getContent();
                    log.info("===[SingleImageComplete#eventSolution] messageContent is " + messageContent);
                    // 获取图片详情
                    MessageAttachment image = event.getMessage().getAttachments().get(0);
                    System.out.println(image.getUrl().toString());
                    System.out.println(image.getProxyUrl().toString());
                }
            }else{
                log.info("===[SingleImageComplete#eventSolution] not botuser");
            }
        }catch (Exception e) {
            log.info("!==[SingleImageComplete#eventSolution] ex is " + e.getMessage());
            e.printStackTrace();
        }

    }
}
