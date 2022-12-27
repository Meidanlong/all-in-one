package com.mdl.springboot.demo.controller.validation;

import com.mdl.springboot.demo.domain.annotation.Log;
import com.mdl.springboot.demo.domain.validation.javax.ValidationPerson;
import com.mdl.springboot.demo.project.diamond.annotation.ApiDoc;
import com.mdl.springboot.demo.service.validation.JavaxService;
import com.mdl.springboot.demo.service.validation.JavaxServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/10/9 15:10
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/springboot/demo/validation/javax/")
public class JavaxController {

    private final static String SUCCESS = "校验通过";
    private final static String FAILED = "校验失败-%s";

    @Autowired
    private JavaxService javaxService;

    /**
     * 测试参数校验*
     * @param name
     * @param age
     * @return
     */
//    @Valid
    @Log("测试GET方法参数校验")
    @GetMapping("get/param")
    public String testGetParam(// @Valid
                               @RequestParam("name") @NotEmpty(message = "名字不能为空")
                                           String name,
                               @RequestParam("age") @NotNull(message = "年龄不能为空") @Min(value = 1, message = "年龄不能小于1岁")
                                       Integer age){
        log.info("==>[JavaxController#testGetParam], name={}, age={}", name, age);
        return SUCCESS;
    }
    @GetMapping("get/param/service")
    public String testGetParamByService(String name,Integer age){
        log.info("==>[JavaxController#testGetParamByService], name={}, age={}", name, age);
        try {
            return javaxService.testGetParam(name, age);
        }catch (ValidationException ex){
            log.error("<==[JavaxController#testGetParamByService] - ex={}", ex.getMessage());
            return String.format(FAILED, ex.getMessage());
        }
    }

    /**
     * 测试参数校验通过BindingResult收集结果*
     *
     * 测试不通过，BindingResult需接在校验对象后面*
     * @param name
     * @param age
     * @param bindingResult
     * @return
     */
    @Deprecated
    @GetMapping("get/param/br")
    public String testGetParamWithBindingResult(@NotEmpty(message = "名字不能为空")
                                                            String name,
                                                @NotNull(message = "年龄不能为空") @Min(value = 1, message = "年龄不能小于1岁")
                                                            Integer age,
                                                BindingResult bindingResult){
        log.info("==>[JavaxController#testGetParamWithBindingResult], name={}, age={}", name, age);
        if (bindingResult.hasErrors()) {
            // throw ...
            return bindingResult.getFieldError().getDefaultMessage();
        }else{
            return SUCCESS;
        }
    }

    /**
     * 测试路径参数校验*
     * @param age
     * @return
     */
    @GetMapping("get/param/{age}")
    public String testGetPathParam(@NotNull(message = "年龄不能为空") @Min(value = 1, message = "年龄不能小于1岁") @PathVariable("age")
                                           Integer age){
        log.info("==>[JavaxController#testGetPathParam], age={}", age);
        return SUCCESS;
    }

    /**
     * 测试对象参数校验*
     * @param vPerson
     * @param bindingResult
     * @return
     */
    @GetMapping("get/obj/br")
    public String testGetObj(@Valid ValidationPerson vPerson, BindingResult bindingResult){
        log.info("==>[JavaxController#testGetObj], name={}, age={}", vPerson.getName(), vPerson.getAge());
        if (bindingResult.hasErrors()) {
            // throw ...
            return bindingResult.getFieldError().getDefaultMessage();
        }else{
            return SUCCESS;
        }
    }
    @GetMapping("get/obj")
    public String testGetObj(@Valid ValidationPerson vPerson){
        log.info("==>[JavaxController#testGetObj], name={}, age={}", vPerson.getName(), vPerson.getAge());
        return SUCCESS;
    }
    @GetMapping("get/obj/service")
    public String testGetObjByService(ValidationPerson vPerson){
        log.info("==>[JavaxController#testGetObjByService] - name={}, age={}", vPerson.getName(), vPerson.getAge());
//        try {
//            return javaxService.testGetObj(vPerson);
//        }catch (ValidationException ex){
//            log.error("<==[JavaxController#testGetObjByService] - ex={}", ex.getMessage());
//            return String.format(FAILED, ex.getMessage());
//        }
        return javaxService.testGetObj(vPerson);
    }

    /**
     * 测试对象参数校验*
     * @param vPerson
     * @param bindingResult
     * @return
     */
    @GetMapping("get/obj/validated/br")
    public String testGetObjWithValidated(@Validated ValidationPerson vPerson, BindingResult bindingResult){
        log.info("==>[JavaxController#testGetObjWithValidated], name={}, age={}", vPerson.getName(), vPerson.getAge());
        if (bindingResult.hasErrors()) {
            // throw ...
            return bindingResult.getFieldError().getDefaultMessage();
        }else{
            return SUCCESS;
        }
    }
    @GetMapping("get/obj/validated")
    public String testGetObjWithValidated(@Validated ValidationPerson vPerson){
        log.info("==>[JavaxController#testGetObjWithValidated], name={}, age={}", vPerson.getName(), vPerson.getAge());
        return SUCCESS;
    }

}
