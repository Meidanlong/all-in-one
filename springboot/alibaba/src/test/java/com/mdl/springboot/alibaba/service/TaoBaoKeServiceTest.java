package com.mdl.springboot.alibaba.service;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @description:淘宝客单测
 * @author: meidanlong
 * @date: 2023/6/18 2:44 PM
 */
@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TaoBaoKeServiceTest {

    @Resource
    private TaoBaoKeService taoBaoKeService;

    @Test
    public void getShop() {
        String result = taoBaoKeService.getShop("男装");
        log.info("getShop result={}", result);
    }

    @Test
    public void optionalMaterial() {
        String result = taoBaoKeService.optionalMaterial();
        log.info("optionalMaterial result={}", result);
    }

    @Test
    public void getItemInfo() {
        List<String> ids = Arrays.asList("123", "456");
        String result = taoBaoKeService.getItemInfo(ids);
        log.info("getItemInfo result={}", result);
    }

    @Test
    public void getTpwdReport() {
        String result = taoBaoKeService.getTpwdReport("12345678");
        log.info("getTpwdReport result={}", result);
    }

    @Test
    public void getItemCats() {
        String result = taoBaoKeService.getItemCats("50011999", null);
        log.info("getItemCats result={}", result);
    }
}