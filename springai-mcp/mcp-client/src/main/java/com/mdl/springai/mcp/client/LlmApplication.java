package com.mdl.springai.mcp.client;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * LLM服务
 *
 * @author meidanlong
 * @date 2025年07月31日
 * @version: 1.0
 */
@SpringBootApplication
public class LlmApplication {

    public static void main(String[] args) {

        // 加载.env文件
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        // 把.env文件中的变量设置到环境变量中
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(LlmApplication.class, args);
    }
}
