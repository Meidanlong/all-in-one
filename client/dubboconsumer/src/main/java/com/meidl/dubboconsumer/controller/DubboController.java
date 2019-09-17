package com.meidl.dubboconsumer.controller;


import com.meidl.dubboconsumer.DemoConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dubbo")
public class DubboController {

    @Autowired
    DemoConsumer demoConsumer;

    @RequestMapping(value = "/hellodubbo")
    @ResponseBody
    public void hellodubbo(){
        demoConsumer.sendMessage("hellodubbo");
    }
}
