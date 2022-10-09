package com.mdl.demo.project.diamond.aspect;

import com.mdl.demo.project.diamond.pojo.ApiParam;
import com.mdl.demo.project.diamond.annotation.ApiDoc;
import com.mdl.demo.project.diamond.pojo.ApiMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
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
            ApiDoc apiAnno = method.getAnnotation(ApiDoc.class);
            if(apiAnno == null){
                continue;
            }
            // 初始化返回结果
            ApiMethod apiMethod = new ApiMethod();
            apiMethod.setAppName(appName);
            apiMethod.setBeanName(beanSimpleName);
            apiMethod.setMethodName(methodName);
            apiMethod.setMethodDesc(apiAnno.desc());
            // 接口需求id，为参数默认需求id
            int methodDemandId = apiAnno.demandId();
            apiMethod.setDemandId(methodDemandId);

            // 获取方法参数名
            LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
            String[] parameterNames = discoverer.getParameterNames(method);
            // 方法参数类型
            Class<?>[] parameterTypes = method.getParameterTypes();

            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for(int i=0; i<parameterAnnotations.length; i++){
                String parameterName = parameterNames[i];
                ApiParam apiParam = null;
                for(Annotation fieldAnnotation : parameterAnnotations[i]){
                    // 参数注释校验
                    if (fieldAnnotation instanceof ApiDoc){
                        apiParam = new ApiParam();
                        ApiDoc paramAnno = (ApiDoc) fieldAnnotation;
                        apiParam.setParamName(parameterName);
                        apiParam.setParamDesc(paramAnno.desc());
                        apiParam.setDemandId(paramAnno.demandId()==0?methodDemandId:paramAnno.demandId());
                        apiParam.setGenerateRule(paramAnno.generateRule());
                        apiParam.setDefaultValue(paramAnno.defaultValue());
                    }
                }

                Class<?> paramObj = parameterTypes[i];
                ApiDoc paramObjAnno = paramObj.getAnnotation(ApiDoc.class);
                // 参数对象注释校验
                if(paramObjAnno == null){
                    if(apiParam != null){
                        // 完善结果
                        apiMethod.getParams().add(apiParam);
                    }
                    continue;
                }
                // 以参数类上注释为准
                if(apiParam == null){
                    apiParam = new ApiParam();
                }
                apiParam.setParamName(parameterName);
                apiParam.setParamDesc(paramObjAnno.desc());
                apiParam.setDemandId(paramObjAnno.demandId()==0?methodDemandId:paramObjAnno.demandId());
                apiParam.setGenerateRule(paramObjAnno.generateRule());
                apiParam.setDefaultValue(paramObjAnno.defaultValue());

                // 参数属性
                Field[] paramObjFields = paramObj.getDeclaredFields();
                if(emptyArray(paramObjFields)){
                    continue;
                }
                for (Field paramObjField : paramObjFields){
                    String paramObjFieldName = paramObjField.getName();
                    ApiDoc paramObjFieldAnno = paramObjField.getAnnotation(ApiDoc.class);
                    if(emptyArray(paramObjFields)){
                        continue;
                    }
                    ApiParam apiParamField = new ApiParam();
                    apiParamField.setParamName(paramObjFieldName);
                    apiParamField.setParamDesc(paramObjFieldAnno.desc());
                    apiParamField.setDemandId(paramObjFieldAnno.demandId()==0?methodDemandId:paramObjFieldAnno.demandId());
                    apiParamField.setGenerateRule(paramObjFieldAnno.generateRule());
                    apiParamField.setDefaultValue(paramObjFieldAnno.defaultValue());
                    apiParam.getFields().add(apiParamField);
                }
                // 完善结果
                apiMethod.getParams().add(apiParam);
            }

            LOGGER.info("### Api is {}" , apiMethod);
        }

        return bean;
    }

    private boolean emptyArray(Object[] arr){
        return arr == null || arr.length <= 0;
    }

}
