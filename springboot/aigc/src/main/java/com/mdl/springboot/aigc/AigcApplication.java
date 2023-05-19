package com.mdl.springboot.aigc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/7/5 10:37
 */
@SpringBootApplication
public class AigcApplication {

    public static ApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(AigcApplication.class);
    }

    public static <T> T getBean(T obj){
        return (T)context.getBean(obj.getClass());
    }
}
