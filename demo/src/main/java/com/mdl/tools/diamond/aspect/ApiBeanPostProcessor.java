package com.mdl.tools.diamond.aspect;

import com.mdl.tools.diamond.pojo.ApiMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/8/30 17:54
 */
@Component
public class ApiBeanPostProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiBeanPostProcessor.class);

    @Value("${spring.application.name}")
    private String appName = "";

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        String beanSimpleName = bean.getClass().getSimpleName();
        Method[] declaredMethods = bean.getClass().getDeclaredMethods();
        if(emptyArray(declaredMethods)){
            return bean;
        }
        for(Method method : declaredMethods){
            String methodName = method.getName();
            com.mdl.tools.diamond.annotation.Api apiAnno = method.getAnnotation(com.mdl.tools.diamond.annotation.Api.class);
            if(apiAnno == null){
                continue;
            }
            // 初始化返回结果
            ApiMethod apiMethod = new ApiMethod();
            apiMethod.setAppName(appName);
            apiMethod.setBeanName(beanSimpleName);

            String apiDesc = apiAnno.desc();
            int requirementId = apiAnno.requirementId();
            LOGGER.info("### app is {}, bean is {}, method is {}, desc is {}, requirementId is {}" , appName, beanSimpleName, methodName, apiDesc, requirementId);

            Class<?>[] parameterTypes = method.getParameterTypes();
            if(emptyArray(parameterTypes)){
                continue;
            }
            // TODO: 2022/8/30 参数直接添加注解情况
            for(Class param : parameterTypes){

                Field[] paramFields = param.getDeclaredFields();
                if(emptyArray(paramFields)){
                    continue;
                }
                for (Field field : paramFields){
                    String fieldName = field.getName();
                    com.mdl.tools.diamond.annotation.Field fieldAnno = field.getAnnotation(com.mdl.tools.diamond.annotation.Field.class);
                    if(emptyArray(paramFields)){
                       continue;
                    }
                    String fieldDesc = fieldAnno.desc();
                    String generateRule = fieldAnno.generateRule();

                }
            }

            LOGGER.info("### bean is {}, method is {}, desc is {}, requirementId is {}" , beanSimpleName, methodName, apiDesc, requirementId);
        }

        return bean;
    }

    private boolean emptyArray(Object[] arr){
        return arr == null || arr.length <= 0;
    }

}
