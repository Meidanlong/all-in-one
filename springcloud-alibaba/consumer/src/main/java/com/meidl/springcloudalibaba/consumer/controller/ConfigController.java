package com.meidl.springcloudalibaba.consumer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/8/5 09:31
 */
@RestController
@RequestMapping("/conf/")
public class ConfigController {

    @Value("${configText}")
    private String configText;

    @GetMapping("configText")
    public String queryConfigText(){
        return configText;
    }

}
