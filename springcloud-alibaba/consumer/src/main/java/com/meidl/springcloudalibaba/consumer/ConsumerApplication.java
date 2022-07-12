package com.meidl.springcloudalibaba.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/7/7 09:19
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
@ComponentScan(value = "com.meidl.springcloudalibaba.consumer.reference")
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class);
    }
}
