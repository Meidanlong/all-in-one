package com.meidl.springcloudalibaba.consumer.reference;

import com.meidl.springcloudalibaba.export.api.IUserRemoteService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/7/7 10:10
 */
@Configuration
public class UserReference {

    @DubboReference(version = "1.0-SNAPSHOT", group = "user")
    public IUserRemoteService userRemoteService;
}
