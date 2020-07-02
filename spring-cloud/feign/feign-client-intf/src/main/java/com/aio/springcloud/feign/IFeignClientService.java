package com.aio.springcloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author meidanlong
 */
@FeignClient("feign-client")
public interface IFeignClientService {

    @GetMapping("/hello")
    public String hello();
}
