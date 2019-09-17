package com.meidl.consumerclient.dubbo.consumer;


import com.alibaba.dubbo.config.annotation.Reference;
import com.meidl.dubbo.api.ServiceAPI;
import org.springframework.stereotype.Component;

@Component
public class DemoConsumer {

    @Reference(url = "dubbo://localhost:20880")
    ServiceAPI serviceAPI;


    public void sendMessage(String message){
        System.out.println("consumer got message");
        String msg = serviceAPI.sendMessage(message);
        System.out.println(msg);
    }
}
