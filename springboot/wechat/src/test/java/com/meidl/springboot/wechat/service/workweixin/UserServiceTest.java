package com.meidl.springboot.wechat.service.workweixin;

import com.meidl.springboot.wechat.WechatApplication;
import com.meidl.springboot.wechat.domain.dto.UserDTO;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/2/21 16:25
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WechatApplication.class)
public class UserServiceTest extends TestCase {

    @Resource
    private UserService userService;

    @Test
    public void testGetUserDetail() {
        UserDTO userDetail = userService.getUserDetail("meidanlong");
        System.out.println(userDetail);

    }

    @Test
    public void testGetUserDetailByMobile() {
        UserDTO userDetail = userService.getUserDetailByMobile("18810606650");
        System.out.println(userDetail);
    }

    @Test
    public void testBatchDeleteUsers() {
        userService.batchDeleteUsers(Arrays.asList("meidanlong"));
    }
}