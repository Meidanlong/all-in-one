package com.mdl.demo.controller.diamond;

import com.mdl.demo.project.diamond.annotation.ApiDoc;
import com.mdl.demo.domain.diamond.Person;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/8/30 19:41
 */
@Component
public class DiamondController {

    @ApiDoc(desc = "测试接口", demandId = 1)
    public String test(@ApiDoc(desc = "数字", demandId = 2, defaultValue = "100") int num, Person person){
        return "test_" + num + "_" + person.getName();
    }
}
