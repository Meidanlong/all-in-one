package com.mdl.tools.test;

import com.mdl.tools.diamond.annotation.Api;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/8/30 19:41
 */
@Component
public class TestController {

    @Api(desc = "测试接口", requirementId = 1)
    public String test(){
        return "test";
    }
}
