package com.mdl.springboot.aigc.service.wenxin.impl;

import com.alibaba.fastjson.JSON;
import com.mdl.springboot.aigc.domain.enums.WenxinyigeImageRatioEnum;
import com.mdl.springboot.aigc.domain.wenxin.ImageDetailRespDTO;
import com.mdl.springboot.aigc.domain.wenxin.ImageTaskRespDTO;
import com.mdl.springboot.aigc.domain.wenxin.Txt2ImgDTO;
import com.mdl.springboot.aigc.service.wenxin.IWenxinyigeService;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class WenxinyigeServiceTest extends TestCase {

    private final static String TASK_STATUS_SUCCESS = "SUCCESS";
    @Resource
    private IWenxinyigeService wenxinyigeService;

    @Test
    public void testTxt2img() {
    }

    @Test
    public void testGetImage() {
    }

    @Test
    public void testTxt2imgThenGetImage() throws InterruptedException {
        // 文生图
        Txt2ImgDTO req = new Txt2ImgDTO();
        req.setPrompt("电影光，华丽高光，电影质感，一张逼真的照片，电影场面，在古代市场和繁忙的街道上，一个25岁的亚洲男性，穿着中国古代长袍，黑色大背发，小眼睛，正常的鼻子，小嘴，一张小脸，看起来好奇和坚定，正在观察市场和思考。");
        req.setImageNum(4);
        req.setChangeDegree(7);
        req.setImageRatio(WenxinyigeImageRatioEnum._1024x1024);

        List<Long> taskIdList = new ArrayList<>();
        for(int i=0; i<5; i++){
            taskIdList.add(wenxinyigeService.txt2img(req));
        }

        TimeUnit.MILLISECONDS.sleep(1000);
        // 结果缓存
        Map<Long, List<String>> resultCache = new HashMap<>();
        // 轮询
        long curTime = System.currentTimeMillis();
        while(System.currentTimeMillis()-curTime<=3*60*1000){
            for(Long taskId : taskIdList){
                // 缓存获取
                List<String> imagelist = resultCache.get(taskId);
                if(!CollectionUtils.isEmpty(imagelist)){
                    continue;
                }
                // 获取图片进度及结果
                ImageTaskRespDTO imageTask = wenxinyigeService.getImage(taskId);
                if(imageTask != null && TASK_STATUS_SUCCESS.equals(imageTask.getTaskStatus())){
                    List<String> imageList = new ArrayList<>();
                    imageTask.getSubTaskResultList().stream().forEach(subTask -> imageList.addAll(subTask.getFinalImageList().stream().map(ImageDetailRespDTO::getImgUrl).collect(Collectors.toList())));
                    resultCache.put(taskId, imageList);
                    return;
                }
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}