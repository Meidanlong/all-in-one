package com.meidl.dubbo.provider.demo;


import com.alibaba.dubbo.config.annotation.Service;
import com.meidl.dubbo.api.ServiceAPI;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = ServiceAPI.class)
public class ServiceImpl implements ServiceAPI {

    @Override
    public String sendMessage(String message) {
        String msg = "provider send message is "+message;
        System.out.println(msg);
        return msg;
    }
}

