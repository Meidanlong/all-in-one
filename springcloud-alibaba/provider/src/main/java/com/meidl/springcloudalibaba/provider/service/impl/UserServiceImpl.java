package com.meidl.springcloudalibaba.provider.service.impl;

import com.meidl.springcloudalibaba.provider.domain.dto.UserDTO;
import com.meidl.springcloudalibaba.provider.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/7/7 09:39
 */
@Service
public class UserServiceImpl implements IUserService {

    @Override
    public UserDTO queryUserById(Long userId) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setUserName("admin");
        return userDTO;
    }
}
