package com.mdl.springboot.aigc.message;

//import configurations.Configuration;

import com.mdl.springboot.aigc.AigcApplication;
import lombok.extern.slf4j.Slf4j;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageType;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ImageGenerateComplete implements MessageCreateListener {

    private static final String MJ_ROBOT_NAME = "Midjourney Bot";
    private static final Long MJ_ROBOT_ID = 936929561302675456L;

    @Autowired
    private IMidjourneyService iMidjourneyService;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        ImageGenerateComplete self = AigcApplication.getBean(this);
        self.eventSolution(event);
    }

    @Async
    public void eventSolution(MessageCreateEvent event) {
        try{
            TextChannel channel = event.getChannel();
            long channelId = channel.getId();
            log.info("===[ImageGenerateComplete#eventSolution] channelId is " + channelId);
            MessageAuthor messageAuthor = event.getMessageAuthor();
            if(messageAuthor.isBotUser()){
                log.info("===[ImageGenerateComplete#eventSolution] isBotUser");
                String robName = messageAuthor.asUser().orElse(null).getName();
                log.info("===[ImageGenerateComplete#eventSolution] robName is " + robName);
                long messageAuthorId = messageAuthor.getId();
                log.info("===[ImageGenerateComplete#eventSolution] messageAuthorId is " + messageAuthorId);
                Message message = event.getMessage();
                long messageId = message.getId();
                log.info("===[ImageGenerateComplete#eventSolution] messageId is " + messageId);
                if(MessageType.NORMAL.equals(message.getType())){
                    HighLevelComponent component = message.getComponents().get(0);
                    ActionRow actionRow = component.asActionRow().orElse(null);
                    LowLevelComponent u1 = actionRow.getComponents().stream().filter(c -> c.asButton().orElse(null).getLabel().orElse("").equals("U1")).findFirst().orElse(null);
                    String customId = u1.asButton().orElse(null).getCustomId().orElse(null);
                    log.info("===[ImageGenerateComplete#eventSolution] customId is " + customId);
                    // 获取单张图片
                    iMidjourneyService.getSingleImage(messageId, customId);
                }
            }else{
                log.info("===[ImageGenerateComplete#eventSolution] not botuser");
            }
        }catch (Exception e) {
            log.info("!==[ImageGenerateComplete#eventSolution] ex is " + e.getMessage());
            e.printStackTrace();
        }
    }


}
