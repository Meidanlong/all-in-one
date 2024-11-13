package com.mdl.springboot.aigc.service.tencent;

import com.alibaba.fastjson.JSON;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.hunyuan.v20230901.models.SubmitHunyuanImageJobRequest;
import com.tencentcloudapi.hunyuan.v20230901.models.TextToImageLiteRequest;
import com.tencentcloudapi.hunyuan.v20230901.models.TextToImageLiteResponse;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO
 *
 * @author meidanlong
 * @date 2024年07月22日
 * @version: 1.0
 */
@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class HunyuanServiceTest extends TestCase {

    @Resource
    private HunyuanService hunyuanService;

    @Test
    public void testTxt2imgLite() throws TencentCloudSDKException {
        TextToImageLiteRequest request = new TextToImageLiteRequest();
        request.setPrompt("远景镜头，中国90年代香港一个宽敞的房间里有许多老人，他们状态各异，有护工夹杂在他们的中间，太阳光，暖色调。（写实）");
        request.setNegativePrompt("扭曲的脸，畸形的手，不符合真实世界的场景");
        request.setStyle("401");
        request.setResolution("1280:720");
        request.setRspImgType("url");
        TextToImageLiteResponse response = hunyuanService.TextToImageLite(request);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void testTxt2imgJob() throws TencentCloudSDKException {
        SubmitHunyuanImageJobRequest request = new SubmitHunyuanImageJobRequest();
        request.setPrompt("中景镜头，一家昏暗的中国私家侦探事务所内，画面中有三个人物，前景是一个侦探的背影坐在办公桌后手里拿着一张照片。他的对面是一对穿着朴素的中国夫妇，丈夫握着女士的手，桌上的灯光投射在他们的脸上照射出他们难过的面容。");
        request.setStyle("xieshi");
        request.setResolution("1280:720");
        request.setLogoAdd(0L);
        request.setRevise(0L);
        List<String> images = hunyuanService.textToImageJob(request);
        System.out.println(JSON.toJSONString(images));
    }
}
