package com.aio.springcloud.controller;

import com.aio.springcloud.feign.IEurekaProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meidanlong
 */
@RestController
@RequestMapping("/hello")
public class HelloContorller {

    @Autowired
    IEurekaProviderService eurekaProviderService;

    @GetMapping
    public String hello(){
        return eurekaProviderService.hello();
    }
}
