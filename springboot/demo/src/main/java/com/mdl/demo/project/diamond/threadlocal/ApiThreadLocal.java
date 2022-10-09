package com.mdl.demo.project.diamond.threadlocal;

import com.mdl.demo.project.diamond.pojo.ApiMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/8/30 20:44
 */
public class ApiThreadLocal {

    static ThreadLocal<List<ApiMethod>> apis = ThreadLocal.withInitial(ArrayList::new);

    public static void addMethodApi(ApiMethod apiMethod){
        if(apiMethod != null){
            apis.get().add(apiMethod);
        }
    }

    public static List<ApiMethod> getMethodApis(){
        return apis.get();
    }
}
