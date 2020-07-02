package com.aio.springcloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author meidanlong
 */
@FeignClient("eureka-provider")
public interface IEurekaProviderService {

    /**
     * hello
     * @return
     */
    @GetMapping("/hello")
    String hello();
}
