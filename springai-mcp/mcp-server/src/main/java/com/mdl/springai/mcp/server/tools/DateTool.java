package com.mdl.springai.mcp.server.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具
 *
 * @author meidanlong
 * @date 2025年08月01日
 * @version: 1.0
 */
@Slf4j
@Component
public class DateTool {

    @Tool(description = "获得当前时间")
    public String getCurrentTime() {
        log.info("========== getCurrentTime==========");
        String currentTime = String.format("当前的时间是 %s",
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        log.info("========== getCurrentTime currentTime:{}==========", currentTime);
        return currentTime;
    }
}
