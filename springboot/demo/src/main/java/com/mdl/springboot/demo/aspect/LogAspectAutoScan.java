package com.mdl.springboot.demo.aspect;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/10/12 15:39
 */
@Slf4j
@Aspect
//@Component
public class LogAspectAutoScan {

    /**
     * 扫包模式(仅扫Controller)
     */
    @Pointcut("within(*com..controller.*.*)")
    public void logPoint() {
    }

    /**
     * 自动记录方法日志(扫包模式)
     *
     * @param joinPoint 切入点
     * @return java.lang.Object 响应信息
     *
     * @author : She Leiming
     * @date : 2019-08-20 15:02:35
     */
    @Around(value = "logPoint()")
    public Object logPointAround(ProceedingJoinPoint joinPoint) throws Throwable {

        String className = joinPoint.getTarget().getClass().getSimpleName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = SingleLocalVariableTableParameterNameDiscoverer.getInstance().getParameterNames(method);
        Object[] requestParams = joinPoint.getArgs();
        if (null != parameterNames) {
            for (int i = 0; i < parameterNames.length; i++) {
                if(requestParams[i] instanceof BindingResult){
                    requestParams[i] = parameterNames[i];
                }else{
                    requestParams[i] = parameterNames[i] + " = " + requestParams[i];
                }
            }
        }
        String classMethodName = className + "#" + method.getName();
        log.info("==>[{}] - param = {}", classMethodName, JSON.toJSONString(requestParams));

        StopWatch timer = new StopWatch();
        timer.start(classMethodName + "_" + System.currentTimeMillis());
        Object responseInfo = joinPoint.proceed();
        timer.stop();

        log.info("<==[{}] - consume {}ms, return = {}", classMethodName, timer.getLastTaskTimeMillis(), JSON.toJSONString(responseInfo));
        return responseInfo;
    }

    /**
     * 用于获取方法参数名所使用到的对象
     *
     * @author : She Leiming
     * @date : 2019-08-20 14:49
     */
    static class SingleLocalVariableTableParameterNameDiscoverer {
        private static volatile LocalVariableTableParameterNameDiscoverer instance = null;

        private SingleLocalVariableTableParameterNameDiscoverer() {
        }

        public static synchronized LocalVariableTableParameterNameDiscoverer getInstance() {
            if (instance == null) {
                instance = new LocalVariableTableParameterNameDiscoverer();
            }
            return instance;
        }
    }
}
