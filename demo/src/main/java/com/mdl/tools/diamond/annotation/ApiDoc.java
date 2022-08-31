package com.mdl.tools.diamond.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/8/30 17:36
 */
@Target({ TYPE, METHOD, FIELD, CONSTRUCTOR, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ApiDoc {

    String desc() default "";

    int demandId() default 0;

    String generateRule() default "";

    String defaultValue() default "";
}
