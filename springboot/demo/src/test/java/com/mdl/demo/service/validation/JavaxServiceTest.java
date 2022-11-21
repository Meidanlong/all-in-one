package com.mdl.demo.service.validation;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/10/9 17:19
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class JavaxServiceTest extends TestCase {

    private final static String SUCCESS = "校验通过";

    @Autowired
    private JavaxService javaxService;

    @Test
    public void testGetParam() {
        assertEquals(SUCCESS, javaxService.testGetParam(null, null));
    }

    @Test
    public void testTestInnerObj() {
        assertEquals(SUCCESS, javaxService.testInnerObj());
    }
}