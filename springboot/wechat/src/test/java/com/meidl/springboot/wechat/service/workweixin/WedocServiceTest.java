package com.meidl.springboot.wechat.service.workweixin;

import com.meidl.springboot.wechat.WechatApplication;
import com.meidl.springboot.wechat.domain.dto.CreateDocDTO;
import com.meidl.springboot.wechat.domain.dto.DocDTO;
import com.meidl.springboot.wechat.domain.enums.DocTypeEnum;
import com.meidl.springboot.wechat.service.workweixin.crop.WedocService;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/2/16 19:22
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WechatApplication.class)
public class WedocServiceTest extends TestCase {

    @Resource
    private WedocService wedocService;

    @Test
    public void testCreateDoc() {
        CreateDocDTO createDocDTO = new CreateDocDTO();
        createDocDTO.setDocType(DocTypeEnum.DOC);
        createDocDTO.setDocName("测试文档-mdl");
        List<String> adminUsers = new ArrayList<>();
        adminUsers.add("meidanlong");
        createDocDTO.setAdminUsers(adminUsers);
        DocDTO doc = wedocService.createDoc(createDocDTO);
        log.info("doc={}", doc);
    }
}