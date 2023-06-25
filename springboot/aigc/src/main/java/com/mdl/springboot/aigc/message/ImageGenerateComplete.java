package com.mdl.springboot.aigc.message;

import com.mdl.common.executor.BizExecutor;
import com.mdl.springboot.aigc.AigcApplication;
import com.mdl.springboot.aigc.service.midjourney.IMidjourneyService;
import com.mdl.springboot.aigc.utils.MidjourneyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.MessageType;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.HighLevelComponent;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Slf4j
@Component
public class ImageGenerateComplete implements MessageCreateListener {

    @Autowired
    private IMidjourneyService midjourneyService;

    /**
     * 注意：如果prompt有MJ非法词汇，则机器人不会收到回调。建议记录生图请求，超时自定义异常
     * @param event The event.
     */
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        try{
            MessageAuthor messageAuthor = event.getMessageAuthor();
            if(messageAuthor.isBotUser()){
                Message message = event.getMessage();
                long messageId = message.getId();
                if(MessageType.NORMAL.equals(message.getType())){
                    HighLevelComponent component = message.getComponents().get(0);
                    ActionRow actionRow = component.asActionRow().get();
                    LowLevelComponent u1 = actionRow.getComponents().stream().filter(c -> c.asButton().get().getLabel().get().equals("U1")).findFirst().get();
                    String customId = u1.asButton().get().getCustomId().get();
                    String prompt = MidjourneyUtil.getReturnPrompt(message.getContent(), true);
                    ImageGenerateComplete self = AigcApplication.getBean(this);
                    BizExecutor.getInstance().getThreadPool().execute(() -> self.generateSingleImage(messageId, prompt, customId));
                }
            }
        }catch (NoSuchElementException e) {
            log.info("!==[ImageGenerateComplete#onMessageCreate] no such element: " + e.getMessage(), e);
            e.printStackTrace();
        }catch (Exception e) {
            log.info("!==[ImageGenerateComplete#onMessageCreate] ex is " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 生成单图
     *
     * 如果是分布式，应考虑重复消息的问题，建议添加分布式锁
     * @param messageId
     * @param prompt
     * @param customId
     */
    public void generateSingleImage(Long messageId, String prompt, String customId) {
        if(StringUtils.isEmpty(prompt)){
            log.error("!==[ImageGenerateComplete#generateSingleImage] - prompt为空");
            return;
        }
        // 获取单张图片
        midjourneyService.getSingleImage(messageId, customId);
    }

}
