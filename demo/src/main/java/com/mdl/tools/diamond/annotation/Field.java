package com.mdl.tools.diamond.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/8/30 17:36
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {

    String desc() default "";

    String generateRule() default "";

    String defaultValue() default "";
}
