package com.mdl.tools.test;

import com.mdl.tools.diamond.annotation.ApiDoc;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/8/30 19:41
 */
@Component
public class TestController {

    @ApiDoc(desc = "测试接口", demandId = 1)
    public String test(@ApiDoc(desc = "数字", demandId = 2, defaultValue = "100") int num, Person person){
        return "test_" + num + "_" + person.getName();
    }
}
