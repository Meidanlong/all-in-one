package com.meidl.springboot.wechat.domain.dto;

import com.mdl.common.domain.BaseObject;
import lombok.Data;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/2/21 16:33
 */
@Data
public class UserDepartmentDTO extends BaseObject {

    private Integer departmentId;

    private Boolean theLeader;
}
