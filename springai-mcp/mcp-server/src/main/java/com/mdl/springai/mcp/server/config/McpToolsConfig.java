package com.mdl.springai.mcp.server.config;

import com.mdl.springai.mcp.server.tools.McpTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * MCP工具配置类
 *
 * @author meidanlong
 * @date 2025年07月31日
 * @version: 1.0
 */
@Slf4j
@Configuration
public class McpToolsConfig {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 注册MCP工具
     * 自动扫描所有实现了McpTool接口的Bean并注册为MCP工具
     */
    @Bean
    public ToolCallbackProvider registMCPTools() {
        // 获取所有实现了McpTool接口的Bean
        Map<String, McpTool> mcpTools = applicationContext.getBeansOfType(McpTool.class);

        // 构建MethodToolCallbackProvider.Builder
        MethodToolCallbackProvider.Builder builder = MethodToolCallbackProvider.builder();

        // 添加所有工具
        mcpTools.forEach((name, tool) -> {
            builder.toolObjects(tool);
            log.info("Registering MCP tool: " + name);
        });

        return builder.build();
    }
}