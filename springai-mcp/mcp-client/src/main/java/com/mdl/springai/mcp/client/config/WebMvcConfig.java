package com.mdl.springai.mcp.client.config;

import com.mdl.springai.mcp.client.interceptor.ResponseInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置类
 *
 * @author meidanlong
 * @date 2025年07月31日
 * @version: 1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public ResponseInterceptor responseInterceptor() {
        return new ResponseInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册响应拦截器，应用于所有请求
        registry.addInterceptor(responseInterceptor())
                .addPathPatterns("/**");
    }
}

