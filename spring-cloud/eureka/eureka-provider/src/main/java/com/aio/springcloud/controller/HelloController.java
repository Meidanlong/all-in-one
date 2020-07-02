package com.aio.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meidanlong
 */
@RestController("/hello")
public class HelloController {

    @Value("${server.port}")
    private String myPort;

    @GetMapping
    public String hello(){
        return String.format("this is consumer-provider, my real port is %s", myPort);
    }
}
