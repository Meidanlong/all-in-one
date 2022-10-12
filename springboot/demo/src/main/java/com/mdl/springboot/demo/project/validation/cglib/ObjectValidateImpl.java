package com.mdl.springboot.demo.project.validation.cglib;

import com.alibaba.fastjson.JSON;
import com.mdl.springboot.demo.project.validation.cglib.base.StringUtil;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 5:33 PM
 */
class ObjectValidateImpl extends ValidateObject implements IObjectValidate {

    public ObjectValidateImpl(String objName, Object object) {
        super(objName, object);
    }

    @Override
    public IObjectValidate sub(String fieldName) {
        return this;
    }

    @Override
    public IObjectValidate sup() {
        return this;
    }

    @Override
    public IObjectValidate supSub(String fieldName) {
        return this;
    }

    @Override
    public IObjectValidate ifThenSub(String fieldNameA, Object expectValueB, String fieldNameC) {
        if(!JSON.toJSONString(reflectGetValue(fieldNameA)).equals(JSON.toJSONString(expectValueB))){
            throw new ValidateException("%s#%s is not %s", super.getObjName(), fieldNameA, JSON.toJSONString(expectValueB));
        }
        return this;
    }

    @Override
    public IObjectValidate ifNotThenSub(String fieldNameA, Object expectValueB, String fieldNameC) {
        if(JSON.toJSONString(reflectGetValue(fieldNameA)).equals(JSON.toJSONString(expectValueB))){
            throw new ValidateException("%s#%s is not %s", super.getObjName(), fieldNameA, JSON.toJSONString(expectValueB));
        }
        return this;
    }

    @Override
    public IObjectValidate ifThenSupSub(String fieldNameA, Object expectValueB, String fieldNameC) {
        if(!JSON.toJSONString(reflectGetValue(fieldNameA)).equals(JSON.toJSONString(expectValueB))){
            throw new ValidateException("%s#%s is not %s", super.getObjName(), fieldNameA, JSON.toJSONString(expectValueB));
        }
        return this;
    }

    @Override
    public IObjectValidate ifNotThenSupSub(String fieldNameA, Object expectValueB, String fieldNameC) {
        if(JSON.toJSONString(reflectGetValue(fieldNameA)).equals(JSON.toJSONString(expectValueB))){
            throw new ValidateException("%s#%s is not %s", super.getObjName(), fieldNameA, JSON.toJSONString(expectValueB));
        }
        return this;
    }

    @Override
    public ValidateUtil build() {
        return new ValidateUtil();
    }

    @Override
    public IObjectValidate notNull(String fieldName){
        Object fieldValue = reflectGetValue(fieldName);
        if(fieldValue == null){
            throw new ValidateException("'%s#%s' should not be null", super.getObjName(), fieldName);
        }else if(fieldValue instanceof String && StringUtil.isEmpty((String) fieldValue)){
            throw new ValidateException("'%s#%s' should not be empty", super.getObjName(), fieldName);
        }else if(fieldValue instanceof List && ((List<?>) fieldValue).size() <= 0){
            throw new ValidateException("'%s#%s' should not be empty", super.getObjName(), fieldName);
        }else{
            setFieldMapData(fieldName, fieldValue);
        }
        return this;
    }

    @Override
    public IObjectValidate notNull(String... fieldNames){
        for(String fieldName : fieldNames){
            notNull(fieldName);
        }
        return this;
    }

    @Override
    public IObjectValidate ifThenMust(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD){
        if(JSON.toJSONString(reflectGetValue(fieldNameA)).equals(JSON.toJSONString(expectValueB))
                && !JSON.toJSONString(reflectGetValue(fieldNameC)).equals(JSON.toJSONString(expectValueD))){
            throw new ValidateException("%s#%s is %s, but %s is not %s", super.getObjName(), fieldNameA, JSON.toJSONString(expectValueB), fieldNameC, JSON.toJSONString(expectValueD));
        }
        return this;
    }

    @Override
    public IObjectValidate ifNotThenMust(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD){
        if(!JSON.toJSONString(reflectGetValue(fieldNameA)).equals(JSON.toJSONString(expectValueB))
                && !JSON.toJSONString(reflectGetValue(fieldNameC)).equals(JSON.toJSONString(expectValueD))){
            throw new ValidateException("%s#%s is not %s, but %s is not %s", super.getObjName(), fieldNameA, JSON.toJSONString(expectValueB), fieldNameC, JSON.toJSONString(expectValueD));
        }
        return this;
    }

    @Override
    public IObjectValidate ifThenMustNot(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD){
        if(JSON.toJSONString(reflectGetValue(fieldNameA)).equals(JSON.toJSONString(expectValueB))
                && JSON.toJSONString(reflectGetValue(fieldNameC)).equals(JSON.toJSONString(expectValueD))){
            throw new ValidateException("%s#%s is %s, but %s is %s", super.getObjName(), fieldNameA, JSON.toJSONString(expectValueB), fieldNameC, JSON.toJSONString(expectValueD));
        }
        return this;
    }

    @Override
    public IObjectValidate ifNotThenMustNot(String fieldNameA, Object expectValueB, String fieldNameC, Object expectValueD){
        if(!JSON.toJSONString(reflectGetValue(fieldNameA)).equals(JSON.toJSONString(expectValueB))
                && JSON.toJSONString(reflectGetValue(fieldNameC)).equals(JSON.toJSONString(expectValueD))){
            throw new ValidateException("%s#%s is not %s, but %s is %s", super.getObjName(), fieldNameA, JSON.toJSONString(expectValueB), fieldNameC, JSON.toJSONString(expectValueD));
        }
        return this;
    }

    @Override
    public IObjectValidate ifNullThenMust(String fieldNameA, String fieldNameC, Object expectValueD) {
        return ifThenMust(fieldNameA, null, fieldNameC, expectValueD);
    }

    @Override
    public IObjectValidate ifNotNullThenMust(String fieldNameA, String fieldNameC, Object expectValueD) {
        return ifNotThenMust(fieldNameA, null, fieldNameC, expectValueD);
    }

    @Override
    public IObjectValidate ifThenMustNotNull(String fieldNameA, Object expectValueB, String fieldNameC) {
        return ifThenMustNot(fieldNameA, expectValueB, fieldNameC, null);
    }

    @Override
    public IObjectValidate ifNotThenMustNotNull(String fieldNameA, Object expectValueB, String fieldNameC) {
        return ifNotThenMustNot(fieldNameA, expectValueB, fieldNameC, null);
    }

    @Override
    public IObjectValidate notNullAtSameTime(String fieldNameA, String fieldNameB){
        ifThenMustNotNull(fieldNameA, null, fieldNameB);
        ifThenMustNotNull(fieldNameB, null, fieldNameA);
        return this;
    }

    @Override
    public IObjectValidate ifNullDefault(String fieldName, Object defaultValue){
        if(reflectGetValue(fieldName) == null){
            populateResetMap(fieldName, defaultValue);
        }
        return this;
    }

    @Override
    public IObjectValidate max(String fieldName, long max){
        try {
            Long toValidate = Long.valueOf(reflectGetValue(fieldName).toString());
            if(toValidate > max){
                throw new ValidateException("value of '%s#%s' should less then '%s'", super.getObjName(), fieldName, String.valueOf(max));
            }
        } catch (Exception e){
            throw new ValidateException("'%s#%s' is null or can not cast to Long", super.getObjName(), fieldName);
        }
        return this;
    }

    @Override
    public IObjectValidate min(String fieldName, long min){
        try {
            Long toValidate = Long.valueOf(reflectGetValue(fieldName).toString());
            if(toValidate < min){
                throw new ValidateException("value of '%s#%s' should more then '%s'", super.getObjName(), fieldName, String.valueOf(min));
            }
        } catch (Exception e){
            throw new ValidateException("'%s#%s' is null or can not cast to Long", super.getObjName(), fieldName);
        }
        return this;
    }

    @Override
    public IObjectValidate between(String fieldName, long min, long max){
        min(fieldName, min);
        max(fieldName, max);
        return this;
    }

    @Override
    public IObjectValidate maxSize(String fieldName, long max){
        try{
            Object colOrMap = reflectGetValue(fieldName);
            if(colOrMap instanceof Map){
                Map map = (Map)colOrMap;
                if(map.size()>max){
                    throw new ValidateException("size of '%s#%s' should less then '%s'", super.getObjName(), fieldName, String.valueOf(max));
                }
            }else if(colOrMap instanceof Collection){
                Collection collection = (Collection) colOrMap;
                if(collection.size()>max){
                    throw new ValidateException("size of '%s#%s' should less then '%s'", super.getObjName(), fieldName, String.valueOf(max));
                }
            }else{
                throw new ValidateException("'%s#%s' is not a instance of Collection or Map", super.getObjName(), fieldName);
            }
        } catch (Exception e){
            throw new ValidateException("'%s#%s' is null or can not cast to Collection or Map", super.getObjName(), fieldName);
        }
        return this;
    }

    @Override
    public IObjectValidate minSize(String fieldName, long min) {
        try{
            Object colOrMap = reflectGetValue(fieldName);
            if(colOrMap instanceof Map){
                Map map = (Map)colOrMap;
                if(map.size()<min){
                    throw new ValidateException("size of '%s#%s' should more then '%s'", super.getObjName(), fieldName, String.valueOf(min));
                }
            }else if(colOrMap instanceof Collection){
                Collection collection = (Collection) colOrMap;
                if(collection.size()<min){
                    throw new ValidateException("size of '%s#%s' should more then '%s'", super.getObjName(), fieldName, String.valueOf(min));
                }
            }else{
                throw new ValidateException("'%s#%s' is not a instance of Collection or Map", super.getObjName(), fieldName);
            }
        } catch (Exception e){
            throw new ValidateException("'%s#%s' is null or can not cast to Collection or Map", super.getObjName(), fieldName);
        }
        return this;
    }

    @Override
    public IObjectValidate phoneNum(String fieldName){
        try{
            String phone = (String) reflectGetValue(fieldName);
            String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            if(!m.matches()){
                throw new ValidateException("'%s#%s' is not a phone number", super.getObjName(), fieldName);
            }
        } catch (Exception e){
            throw new ValidateException("'%s#%s' is null or can not cast to String", super.getObjName(), fieldName);
        }
        return this;
    }

    @Override
    public IObjectValidate expression(String exprName, Function<Object,Boolean> expression) {
        try{
            Boolean success = expression.apply(this.getCurObj());
            if(!success){
                throw new ValidateException("expression '%s' is not true", exprName);
            }
        }catch (Exception e){
            throw new ValidateException("expression '%s' is not legal", exprName);
        }
        return this;
    }

    /**
     * 反射获取值
     *
     * 区分普通对象和Map对象
     * @param fieldName
     * @return
     * @throws ValidateException
     */
    public Object reflectGetValue(String fieldName){
        try{
            Object fieldValue = getFieldMapData(fieldName);
            if(fieldValue != null){
                return fieldValue;
            }
            Object obj = super.getCurObj();
            if(obj instanceof Map){
                return ((Map<String, Object>) obj).get(fieldName);
            }
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException e) {
            throw new ValidateException("'%s' does not have field named '%s'", super.getObjName(), fieldName);
        } catch (IllegalAccessException e) {
            throw new ValidateException("can not access field '%s#%s'", super.getObjName(), fieldName);
        }
    }
}
