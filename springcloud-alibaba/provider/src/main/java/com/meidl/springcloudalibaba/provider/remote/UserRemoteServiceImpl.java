package com.meidl.springcloudalibaba.provider.remote;

import com.meidl.springcloudalibaba.export.api.IUserRemoteService;
import com.meidl.springcloudalibaba.export.domain.UserModel;
import com.meidl.springcloudalibaba.provider.domain.adapter.UserAdapter;
import com.meidl.springcloudalibaba.provider.domain.dto.UserDTO;
import com.meidl.springcloudalibaba.provider.service.IUserService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/7/7 09:36
 */
@DubboService(version = "1.0-SNAPSHOT", group = "user")
public class UserRemoteServiceImpl implements IUserRemoteService {

    @Resource
    private IUserService userService;

    @Override
    public UserModel queryUserById(Long userId) {
        UserDTO userDTO = userService.queryUserById(userId);
        UserModel userModel = UserAdapter.dto2model(userDTO);
        return userModel;
    }
}
