package com.mdl.common.validation.annotation;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/9/5 11:19
 */


import com.mdl.common.validation.validator.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义手机号约束注解
 */
@Documented
// 注解的作用目标
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
// 注解的保留策略
@Retention(RetentionPolicy.RUNTIME)
// 不同之处：于约束注解关联的验证器
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {

    // 约束注解验证时的输出信息
    String message() default "手机号校验错误";

    // 约束注解在验证时所属的组别
    Class<?>[] groups() default {};

    // 约束注解的有效负载
    Class<? extends Payload>[] payload() default {};
}