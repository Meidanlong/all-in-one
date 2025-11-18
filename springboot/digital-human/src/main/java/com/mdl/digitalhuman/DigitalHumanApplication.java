package com.mdl.digitalhuman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 数字人项目主应用类
 *
 * @author meidanlong
 * @date 2025年11月18日
 * @version: 1.0
 */
@SpringBootApplication
public class DigitalHumanApplication {
    public static void main(String[] args) {
        SpringApplication.run(DigitalHumanApplication.class, args);
        System.out.println("数字人项目启动成功！前端访问：http://localhost:8080");
    }

    /**
     * 静态资源配置
     */
    @Configuration
    static class WebMvcConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            // 配置静态资源访问路径
            registry.addResourceHandler("/**")
                    .addResourceLocations("classpath:/digital-human-front/");

            // 配置模型文件访问路径
            registry.addResourceHandler("/model/**")
                    .addResourceLocations("classpath:/digital-human-front/public/model/");
        }
    }
}