package com.meidl.springcloudalibaba.consumer.controller;

import com.meidl.springcloudalibaba.consumer.domain.adapter.UserAdapter;
import com.meidl.springcloudalibaba.consumer.domain.vo.UserVO;
import com.meidl.springcloudalibaba.export.api.IUserRemoteService;
import com.meidl.springcloudalibaba.export.domain.UserModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/7/7 10:14
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Resource
    private IUserRemoteService userRemoteService;

    @RequestMapping("query/{userId}")
    public UserVO queryUserById(@PathVariable Long userId){
        UserModel userModel = userRemoteService.queryUserById(userId);
        UserVO userVO = UserAdapter.model2vo(userModel);
        return userVO;
    }
}
