package com.mdl.springboot.demo.domain.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:接口日志
 * @author: meidanlong
 * @date: 2022/11/27 5:01 PM
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 接口描述
     * @return
     */
    String description() default "";

    @AliasFor("description")
    String value() default "";

}
