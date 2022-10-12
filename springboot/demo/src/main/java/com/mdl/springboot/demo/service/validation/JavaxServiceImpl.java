package com.mdl.springboot.demo.service.validation;

import com.mdl.springboot.demo.domain.validation.javax.ValidationPerson;
import com.mdl.springboot.demo.project.diamond.annotation.ApiDoc;
import com.mdl.springboot.demo.utils.ExceptionUtil;
import com.mdl.springboot.demo.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
public class JavaxServiceImpl implements JavaxService{

    private final static String SUCCESS = "校验通过";
    private final static String FAILED = "校验失败-%s";

    @Override
    @ApiDoc
    public String testGetParam(String name, Integer age){
        log.info("==>[JavaxService#testGetParam], name={}, age={}", name, age);
        return SUCCESS;
    }

    @Override
    public String testGetObj(ValidationPerson vPerson){
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
            log.error("<==[JavaxService#testInnerObj] - ex={}, trace={}", ve.getMessage(), ExceptionUtil.stackTrace(ve));
            return String.format(FAILED, ve.getMessage());
        }

        return SUCCESS;
    }
}
