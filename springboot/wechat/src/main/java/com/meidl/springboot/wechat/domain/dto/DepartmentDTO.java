package com.meidl.springboot.wechat.domain.dto;

import com.mdl.common.domain.BaseObject;
import lombok.Data;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/2/22 19:44
 */
@Data
public class DepartmentDTO extends BaseObject {

    private Integer departmentId;

    private String departmentName;
}
