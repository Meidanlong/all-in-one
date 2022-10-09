package com.mdl.demo.service.validation;

import com.mdl.demo.domain.validation.javax.ValidationPerson;
import com.mdl.demo.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/10/9 16:50
 */
@Slf4j
@Validated
@Service
public class JavaxService {

    private final static String SUCCESS = "校验通过";
    private final static String FAILED = "校验失败-%s";


    public String testGetParam(@NotEmpty(message = "名字不能为空")
                                       String name,
                               @NotNull(message = "年龄不能为空") @Min(value = 1, message = "年龄不能小于1岁")
                                       Integer age){
        log.info("==>[JavaxService#testGetParam], name={}, age={}", name, age);
        return SUCCESS;
    }

    public String testGetObj(@Valid ValidationPerson vPerson){
        log.info("==>[JavaxService#testGetObj], name={}, age={}", vPerson.getName(), vPerson.getAge());
        return SUCCESS;
    }

    public String testInnerObj(){
        ValidationPerson validationPerson = new ValidationPerson();
        validationPerson.setName("mdl");
        validationPerson.setAge(0);
        try {
            ValidateUtil.validate(validationPerson);
        }catch (ValidationException ve){
            log.error("<==[JavaxService#testInnerObj] - ex={}", ve.getMessage());
            return String.format(FAILED, ve.getMessage());
        }

        return SUCCESS;
    }
}
