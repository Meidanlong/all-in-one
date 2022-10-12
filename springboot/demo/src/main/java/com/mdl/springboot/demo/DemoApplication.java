package com.mdl.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/8/30 19:44
 */
@ComponentScan({"com.mdl.springboot.**", "java.lang", "org.springframework.web.bind.annotation"})
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
