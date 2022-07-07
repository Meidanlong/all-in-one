package com.meidl.springcloudalibaba.provider.domain.adapter;

import com.meidl.springcloudalibaba.export.domain.UserModel;
import com.meidl.springcloudalibaba.provider.domain.dto.UserDTO;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/7/7 09:40
 */
public class UserAdapter {

    public static UserModel dto2model(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        userModel.setUserId(userDTO.getUserId());
        userModel.setUserName(userDTO.getUserName());
        return userModel;
    }
}
