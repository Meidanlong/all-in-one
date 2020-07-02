package com.aio.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author meidanlong
 */
@RestController
@RequestMapping("/")
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("hello")
    public String hello(){
        return restTemplate.getForObject("http://eureka-provider/hello",String.class);
    }
}
