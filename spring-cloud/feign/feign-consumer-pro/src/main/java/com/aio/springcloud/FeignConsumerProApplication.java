package com.aio.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meidanlong
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.aio.springcloud.*")
public class FeignConsumerProApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeignConsumerProApplication.class,args);
    }

}
