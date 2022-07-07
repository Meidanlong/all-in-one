package com.meidl.springcloudalibaba.export.api;


import com.meidl.springcloudalibaba.export.domain.UserModel;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/7/7 09:31
 */
public interface IUserRemoteService {

    UserModel queryUserById(Long userId);
}
