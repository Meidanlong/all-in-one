package com.mdl.springai.mcp.server;

import com.mdl.springai.mcp.server.tools.DateTool;
import com.mdl.springai.mcp.server.tools.MovieTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Mcp服务
 *
 * @author meidanlong
 * @date 2025年07月31日
 * @version: 1.0
 */
@SpringBootApplication
public class McpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpServerApplication.class, args);
    }

    /**
     * 注册MCP工具
     */
    @Bean
    public ToolCallbackProvider registMCPTools(DateTool dateTool, MovieTool movieTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(dateTool, movieTool)
                .build();
    }
}
