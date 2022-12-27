package com.mdl.springboot.demo.controller.diamond;

import com.mdl.springboot.demo.domain.annotation.Log;
import com.mdl.springboot.demo.project.diamond.annotation.ApiDoc;
import com.mdl.springboot.demo.domain.diamond.Person;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/8/30 19:41
 */
@Component
public class DiamondController {

    @ApiDoc(desc = "测试接口", demandId = 1)
    @Log("钻石需求面板测试接口")
    public String test(@ApiDoc(desc = "数字", demandId = 2, defaultValue = "100") int num, Person person){
        return "test_" + num + "_" + person.getName();
    }
}
