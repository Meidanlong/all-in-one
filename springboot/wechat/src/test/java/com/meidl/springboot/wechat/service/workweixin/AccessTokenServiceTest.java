package com.meidl.springboot.wechat.service.workweixin;

import com.meidl.springboot.wechat.WechatApplication;
import com.meidl.springboot.wechat.service.workweixin.crop.AccessTokenService;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/2/16 17:21
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WechatApplication.class)
public class AccessTokenServiceTest extends TestCase {

    @Resource
    private AccessTokenService accessTokenService;

    @Test
    public void testGetAccessToken() {
        String accessToken = accessTokenService.getAccessToken();
        log.info("accessToken={}", accessToken);
    }
}