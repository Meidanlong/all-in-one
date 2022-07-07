package com.meidl.springcloudalibaba.provider.service;

import com.meidl.springcloudalibaba.provider.domain.dto.UserDTO;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/7/7 09:39
 */
public interface IUserService {

    UserDTO queryUserById(Long userId);
}
