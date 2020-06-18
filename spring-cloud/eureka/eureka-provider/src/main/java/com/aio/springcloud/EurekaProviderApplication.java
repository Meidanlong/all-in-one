package com.aio.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EurekaProviderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaProviderApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
