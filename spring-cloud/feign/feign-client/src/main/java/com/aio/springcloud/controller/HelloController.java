package com.aio.springcloud.controller;

import com.aio.springcloud.feign.IFeignClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meidanlong
 */
@RestController
public class HelloController implements IFeignClientService {

    @Value("${server.port}")
    private String myPort;

    @Override
    public String hello(){
        return String.format("this is feign-client, my real port is %s", myPort);
    }
}
