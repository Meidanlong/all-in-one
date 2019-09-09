package com.meidl.consumerclient.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping(value = "/netty")
public class NettyController {

    @RequestMapping(value = "/hellonetty")
    public String redirect(){return  "hellonetty";}
}
