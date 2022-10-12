package com.mdl.springboot.demo.exception;

import com.alibaba.fastjson.JSON;
import com.mdl.springboot.demo.project.validation.jdk.exception.ErrorCodeEnum;
import com.mdl.springboot.demo.project.validation.jdk.exception.ValidationException;
import com.mdl.springboot.demo.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/10/12 14:49
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final static String FAILED = "校验失败-%s";

    /**
     * 拦截业务类异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public String businessExceptionHandle(ConstraintViolationException e) {
        log.error("!!![ValidationException] - ex = {}", e.getMessage());
        return String.format(FAILED, e.getMessage());
    }

    /**
     * 拦截运行时异常
     * @param e
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public String runtimeExceptionHandle(RuntimeException e) {
        log.error("!!![RuntimeException] - ex = {}, trace = {}", e.getMessage(), ExceptionUtil.stackTrace(e));
        return String.format(FAILED, e.getMessage());
    }
}
