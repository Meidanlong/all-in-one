package com.mdl.springboot.demo.aspect;

import com.alibaba.fastjson.JSON;
import com.mdl.springboot.demo.domain.annotation.Log;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:日志切片
 * @author: meidanlong
 * @date: 2022/11/27 5:05 PM
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Aspect
@Component
public class LogAspect {

    private final static String CLASS_METHOD_NAME = "[%s#%s]";

    @Pointcut("@annotation(com.mdl.springboot.demo.domain.annotation.Log)")
    public void pointCut(){
    }

    @Around("pointCut()")
    public Object pointCut(ProceedingJoinPoint joinPoint) throws Throwable {
        LogDetail logDetail = populateLogDetail(joinPoint);
        String classMethodName = String.format(CLASS_METHOD_NAME, logDetail.getClazz().getSimpleName(), logDetail.getMethod().getName());
        // 初始化计时器
        StopWatch timer = new StopWatch();
        timer.start(Thread.currentThread().getId() + "_" + classMethodName + "_" + System.currentTimeMillis());
        try{
            log.info("==>{} - {} : params={}", classMethodName, logDetail.getDesc(), JSON.toJSONString(logDetail.args));
            // 执行业务逻辑
            Object result = joinPoint.proceed();
            timer.stop();
            log.info("<=={} - {} - cost={}ms : result={}, params={}", classMethodName, logDetail.getDesc(), timer.getLastTaskTimeMillis(), JSON.toJSONString(result), JSON.toJSONString(logDetail.args));
            return result;
        }catch (Exception ex){
            timer.stop();
            log.info("!=={} - {} - cost={}ms : 未知异常，ex={}，参考error.log - params={}", classMethodName, logDetail.getDesc(), timer.getLastTaskTimeMillis(), ex.getMessage(), JSON.toJSONString(logDetail.args));
            log.error("!!!{} - {} : {} - params={}, trace={}", classMethodName, logDetail.getDesc(), ex.getMessage(), JSON.toJSONString(logDetail.args), exceptionStackTrace(ex), ex);
            throw ex;
        }
    }

    private LogDetail populateLogDetail(ProceedingJoinPoint joinPoint) {
        LogDetail logDetail = new LogDetail();
        // 通过joinPoint获取被注解方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 1、获取注解参数值
        Log log = methodSignature.getMethod().getAnnotation(Log.class);
        String logDescription = log.description();
        if(StringUtils.isBlank(logDescription)){
            logDescription = log.value();
            if(StringUtils.isBlank(logDescription)){
                logDescription = "方法无描述";
            }
        }
        logDetail.setDesc(logDescription);
        // 2、获取方法/类
        Method method = methodSignature.getMethod();
        logDetail.setMethod(method);
        logDetail.setClazz(method.getDeclaringClass());
        // 3、获取参数值
        Map<String, Object> argMap = new HashMap<>();
        Object[] args = joinPoint.getArgs();
        //获取运行时参数的名称
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = discoverer.getParameterNames(method);
        for (int i = 0; i < parameterNames.length; i++) {
            argMap.put(parameterNames[i],args[i]);
        }
        logDetail.setArgs(JSON.toJSONString(argMap));
        return logDetail;
    }

    private String exceptionStackTrace(Exception e){
        StackTraceElement[] stackTrace = e.getStackTrace();
        if(stackTrace != null && stackTrace.length > 0){
            List<String> traceElementList = Arrays.stream(stackTrace).map(st -> String.format("%s#%s#%d", !StringUtils.isBlank(st.getFileName())?st.getFileName().split(".java")[0]: Strings.EMPTY, st.getMethodName(), st.getLineNumber())).collect(Collectors.toList());
            return JSON.toJSONString(traceElementList);
        }
        return Strings.EMPTY;
    }

    @Getter
    @Setter
    class LogDetail{
        private Class clazz;
        private Method method;
        private String args;
        private String desc;
    }
}
