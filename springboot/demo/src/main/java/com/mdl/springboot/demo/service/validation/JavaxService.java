package com.mdl.springboot.demo.service.validation;

import com.mdl.springboot.demo.domain.validation.javax.ValidationPerson;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/10/12 15:24
 */
public interface JavaxService {

    String testGetParam(@NotEmpty(message = "名字不能为空")
                                String name,
                        @NotNull(message = "年龄不能为空") @Min(value = 1, message = "年龄不能小于1岁")
                                Integer age);

    String testGetObj(@Valid ValidationPerson vPerson);
}
