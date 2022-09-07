package com.mdl.tools.validation.cglib;


import com.mdl.tools.validation.cglib.base.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/1/23 5:45 PM
 */
public class ValidateUtil extends ValidateStruct implements InvocationHandler {

    private final String[] invokeThis = {"sub", "sup", "supSub"};

    private IObjectValidate objectValidate;
    private Object curObj;
    private IExpressionValidate expressionValidate;
    private ValidateEnum validateType;
    private int skipMethodState = 0;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args){
        // 返回方法
        String methodName = method.getName();
        if("build".equals(methodName)){
            return build();
        }
        // 代理逻辑
        try{
            if(isValid()){
                // 校验方法参数
                validateArgs(methodName, args);
                // 对象校验
                if(ValidateEnum.OBJECT == validateType){
                    // 层级变更
                    if(Arrays.asList(invokeThis).contains(method.getName())){
                        Method thisMethod = ValidateUtil.class.getDeclaredMethod(method.getName(), method.getParameterTypes());
                        thisMethod.setAccessible(true);
                        return thisMethod.invoke(this, args);
                    }
                    if(skipMethodState == 0){
                        // 该层级对象方法调用
                        List<ObjectValidateImpl> list = peekValidateObjectList();
                        for(ObjectValidateImpl obj : list){
                            method.invoke(obj, args);
                            // 可为原对象赋值
                            if(!obj.getResetMap().isEmpty()){
                                reflectSetValue(obj);
                                // 清空resetMap
                                obj.removeResetMap();
                            }
                        }
                        // 如果方法以"ThenSup"或"ThenSub"结尾，则执行指定栈层级变更
                        if(methodName.endsWith("ThenSub")){
                            // 按照约定，最后一个参数为下一层级的fieldName
                            String subFieldName = (String) args[args.length -1];
                            sub(subFieldName);
                        }else if(methodName.endsWith("ThenSupSub")){
                            String subFieldName = (String) args[args.length -1];
                            supSub(subFieldName);
                        }else if(methodName.endsWith("ThenSup")){
                            sup();
                        }
                    }
                }
                // 表达式校验
                if(ValidateEnum.EXPRESSION == validateType){
                    method.invoke(new ExpressionValidateImpl(), args);
                }
            }
        } catch (ValidateException | NoSuchMethodException e) {
            populateError(e.getMessage());
        } catch (InvocationTargetException e) {
            // 层级执行方法捞回
            // 如果方法以"ThenSup"或"ThenSub"结尾，则执行指定栈层级变更
            if(methodName.endsWith("ThenSub") || methodName.endsWith("ThenSupSub")){
                skipMethodState++;
            }else if(methodName.endsWith("ThenSup")){
                sup();
            }else{
                populateError(e.getTargetException().getMessage());
            }
        } catch (IllegalAccessException e) {
            populateError(e.getMessage());
        } catch (NoSuchFieldException e) {
            populateError(e.getMessage());
        }
        return ValidateEnum.OBJECT == validateType ? objectValidate : expressionValidate;
    }

    /**
     * 获取对象校验方法
     * @param objName
     * @param getObj
     * @return
     */
    public IObjectValidate object(String objName, Supplier getObj){
        validateType = ValidateEnum.OBJECT;
        objectValidate = (IObjectValidate) Proxy.newProxyInstance(
                ObjectValidateImpl.class.getClassLoader(),
                ObjectValidateImpl.class.getInterfaces(),
                this);
        if(StringUtil.isEmpty(objName)){
            populateError("objName is empty");
            return objectValidate;
        }
        if(getObj == null){
            populateError("function getObj is null");
            return objectValidate;
        }
        curObj = getObj.get();
        if(curObj == null){
            populateError("%s is null", objName);
            return objectValidate;
        }
        List<ObjectValidateImpl> list = getObjListOfField(objName, curObj);
        pushValidateObjectList(list);
        return objectValidate;
    }

    /**
     * 获取表达式校验方法
     * @return
     */
    public IExpressionValidate expression(){
        validateType = ValidateEnum.EXPRESSION;
        expressionValidate = (IExpressionValidate) Proxy.newProxyInstance(
                ExpressionValidateImpl.class.getClassLoader(),
                ExpressionValidateImpl.class.getInterfaces(),
                this);
        return expressionValidate;
    }

    /**
     * 构建校验类对象方法
     * @return
     */
    private ValidateUtil build(){
        return this;
    }

    /**
     * 校验方法调用参数
     * @param methodName
     * @param args
     */
    private void validateArgs(String methodName, Object[] args) throws ValidateException {
        if(args != null){
            for(Object arg : args){
                if(arg == null){
                    throw new ValidateException("there has null arg, when call method '%s'", methodName);
                }
                if(arg instanceof String && StringUtil.isEmpty((String)arg)){
                    throw new ValidateException("there has null arg, when call method '%s'", methodName);
                }
                if(arg.getClass().isArray()){
                    validateArgs(methodName, (Object[])arg);
                }
            }
        }
    }

    /**
     * 获取属性对象列表
     * @param name
     * @param obj
     * @return
     */
    private List<ObjectValidateImpl> getObjListOfField(String name, Object obj){
        List<ObjectValidateImpl> list = new ArrayList<>();
        // 单独转化数组类型
        if(obj.getClass().isArray()){
            obj = Arrays.asList(obj);
        }
        if(obj instanceof Collection){
            Collection collectionObj = (Collection) obj;
            Iterator iterator = collectionObj.iterator();
            int index = 0;
            while (iterator.hasNext()){
                Object next = iterator.next();
                list.add(new ObjectValidateImpl(String.format("%s_%d", name, index++), next));
            }
        }else{
            list.add(new ObjectValidateImpl(name, obj));
        }
        return list;
    }

    /**
     * 获取下一层级对象
     * @param fieldName
     * @return
     */
    private IObjectValidate sub(String fieldName){
        List<ObjectValidateImpl> validateObjectList = peekValidateObjectList();
        List<ObjectValidateImpl> subValidateObjectList = reflectGetValueList(validateObjectList, fieldName);
        pushValidateObjectList(subValidateObjectList);
        return objectValidate;
    }

    /**
     * 获取上一层级对象
     * @return
     */
    private IObjectValidate sup(){
        // 更新skipMethodState状态
        if(skipMethodState > 0){
            skipMethodState--;
        }else{
            popValidateObjectList();
            if(stackIsEmpty()){
                populateError("there is no super object, please check call stack");
            }
        }
        return objectValidate;
    }

    /**
     * 获取同级对象
     * @param fieldName
     * @return
     */
    private IObjectValidate supSub(String fieldName){
        sup().sub(fieldName);
        return objectValidate;
    }

    /**
     * 反射为复杂对象设置值
     * @param objValid
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private IObjectValidate reflectSetValue(ObjectValidateImpl objValid) throws NoSuchFieldException, IllegalAccessException {
        String[] partObjValid = objValid.getObjName().split("#");
        Object __curObj = curObj;
        if(partObjValid[0].contains("_")){
            Integer curObjListIndex = Integer.valueOf(partObjValid[0].split("_")[1]);
            List curObjList = (List) curObj;
            __curObj = curObjList.get(curObjListIndex);
        }
        if(partObjValid.length > 1){
            for(int i=1; i<partObjValid.length; i++){
                __curObj = getSubObj(__curObj, partObjValid[i]);
            }
        }
        Map<String, Object> resetMap = objValid.getResetMap();
        for(String key : resetMap.keySet()){
            Field field = __curObj.getClass().getDeclaredField(key);
            field.setAccessible(true);
            field.set(__curObj, resetMap.get(key));
        }
        return objectValidate;
    }

    /**
     * 获取子类对象
     * @param obj
     * @param curFieldName
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private Object getSubObj(Object obj, String curFieldName) throws NoSuchFieldException, IllegalAccessException {
        if(curFieldName.contains("_")){
            String[] partCurFieldName = curFieldName.split("_");
            String fieldName = partCurFieldName[0];
            Integer listIndex = Integer.valueOf(partCurFieldName[1]);
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            List subObjList = (List) field.get(obj);
            Object subObj = subObjList.get(listIndex);
            return subObj;
        }else{
            Field field = obj.getClass().getDeclaredField(curFieldName);
            field.setAccessible(true);
            return field.get(obj);
        }
    }

    /**
     * 反射获取对象属性下一级对象列表
     * @param validateObjectList
     * @param fieldName
     * @return
     */
    private List<ObjectValidateImpl> reflectGetValueList(List<ObjectValidateImpl> validateObjectList, String fieldName){
        List<ObjectValidateImpl> subList = new ArrayList<>();
        for(ObjectValidateImpl objectValidate : validateObjectList){
            objectValidate.notNull(fieldName);
            Object obj = objectValidate.getFieldMapData(fieldName);
            String objName = String.format("%s#%s", objectValidate.getObjName(), fieldName);
            List<ObjectValidateImpl> addList = getObjListOfField(objName, obj);
            subList.addAll(addList);
        }
        return subList;
    }
}
