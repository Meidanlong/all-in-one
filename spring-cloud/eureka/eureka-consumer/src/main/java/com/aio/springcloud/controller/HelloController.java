package com.aio.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author meidanlong
 */
@Slf4j
@RestController("/hello")
public class HelloController {

    /**
     * spring 自带的负载均衡客户端
     */
    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 使用简单负载均衡和restTemplate进行调用
     * @return
     */
    @GetMapping
    public String hello(){
        ServiceInstance serviceInstance = loadBalancer.choose("eureka-provider");

        if(serviceInstance == null){
            return "No available instance";
        }

        String target = String.format("http://%s:%s",serviceInstance.getHost(),serviceInstance.getPort());

        log.info("url is {}",target);

        return restTemplate.getForObject(target, String.class);
    }
}
