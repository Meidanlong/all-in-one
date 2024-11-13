package com.mdl.springboot.aigc.service.wenxin.impl;

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
    private final static String TASK_STATUS_FAILED = "FAILED";
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
        // 写实
//        String pre = "电影光，华丽高光，电影质感，一张逼真的照片，电影场面，";
        // 3d 动画
        String pre = "迪士尼风格，CG 3D动画，动画电影插图， ";
        // 线稿
//        String pre = "素描动漫插画，黑白风格，";
        // 动漫
//        String pre = "新海诚动漫风格，日本动画，动画电影插图，";
        String prompt = "中景镜头，侧视图，中国一栋居民楼走廊里，穿着黑色夹克，黑色裤子的寸头男人生气的指着一个穿着灰色旗袍双手叉腰的女人，两个人正在激烈的争吵弥漫着紧张的气氛，蓝灰色调，逆光（3d动画）";
        String suf = "。 请注意：画面中一定不要出现坏手、坏脸等不符合现实世界的元素！";
//        req.setPrompt(pre + prompt + suf);
        req.setPrompt(pre + prompt);
        req.setImageNum(1);
        req.setChangeDegree(9);
        req.setImageRatio(WenxinyigeImageRatioEnum._1280x720);

        List<Long> taskIdList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            taskIdList.add(wenxinyigeService.txt2img(req));
        }

        TimeUnit.MILLISECONDS.sleep(1000);
        // 结果缓存
        Map<Long, List<String>> resultCache = new HashMap<>();
        // 轮询
        long curTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - curTime <= 3 * 60 * 1000) {
            for (Long taskId : taskIdList) {
                // 缓存获取
                List<String> imagelist = resultCache.get(taskId);
                if (!CollectionUtils.isEmpty(imagelist)) {
                    continue;
                }
                // 获取图片进度及结果
                ImageTaskRespDTO imageTask = wenxinyigeService.getImage(taskId);
                if (imageTask != null) {
                    if (TASK_STATUS_SUCCESS.equals(imageTask.getTaskStatus())) {
                        List<String> imageList = new ArrayList<>();
                        imageTask.getSubTaskResultList().stream().forEach(subTask -> imageList.addAll(subTask.getFinalImageList().stream().map(ImageDetailRespDTO::getImgUrl).collect(Collectors.toList())));
                        resultCache.put(taskId, imageList);
                        return;
                    }
                    if (TASK_STATUS_FAILED.equals(imageTask.getTaskStatus())) {
                        return;
                    }
                }
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}