package com.meidl.springboot.wechat.domain.dto;

import com.mdl.common.domain.BaseObject;
import com.meidl.springboot.wechat.domain.enums.UserGenderEnum;
import com.meidl.springboot.wechat.domain.enums.UserStatusEnum;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/2/21 16:28
 */
@Data
public class UserDTO extends BaseObject {

    private String userId;

    private String name;

    private UserGenderEnum gender;

    private List<UserDepartmentDTO> departments;

    private String position;

    private String mobile;

    private String email;

    private List<String> directLeaders;

    private String avatar;

    private UserStatusEnum status;
}
