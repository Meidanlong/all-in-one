package com.meidl.dubboconsumer;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
public class DubboconsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboconsumerApplication.class, args);
    }

}
