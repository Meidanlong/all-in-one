package com.meidl.consumerclient;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
public class ConsumerClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerClientApplication.class, args);
    }

}
