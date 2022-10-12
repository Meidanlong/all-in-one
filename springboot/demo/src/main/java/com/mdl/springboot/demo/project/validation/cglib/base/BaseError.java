package com.mdl.springboot.demo.project.validation.cglib.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: meidanlong
 * @date: 2021/3/21 8:16 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseError extends BaseObject {

    private Integer code;

    private String message;

}
