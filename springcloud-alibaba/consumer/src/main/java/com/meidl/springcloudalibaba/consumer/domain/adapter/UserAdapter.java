package com.meidl.springcloudalibaba.consumer.domain.adapter;

import com.meidl.springcloudalibaba.consumer.domain.vo.UserVO;
import com.meidl.springcloudalibaba.export.domain.UserModel;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/7/7 10:12
 */
public class UserAdapter {

    public static UserVO model2vo(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        userVO.setUserId(userModel.getUserId());
        userVO.setUserName(userModel.getUserName());
        return userVO;
    }
}
