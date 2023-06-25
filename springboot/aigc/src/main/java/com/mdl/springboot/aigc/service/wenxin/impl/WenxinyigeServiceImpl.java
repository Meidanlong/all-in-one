package com.mdl.springboot.aigc.service.wenxin.impl;

import com.alibaba.fastjson.JSON;
import com.mdl.common.domain.BusinessException;
import com.mdl.common.utils.HttpUtil;
import com.mdl.common.utils.StringUtil;
import com.mdl.springboot.aigc.domain.enums.WenxinyigeImageRatioEnum;
import com.mdl.springboot.aigc.domain.wenxin.*;
import com.mdl.springboot.aigc.service.wenxin.IAccessTokenService;
import com.mdl.springboot.aigc.service.wenxin.IWenxinyigeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 文心一格服务
 *
 *  {@link <a href="https://cloud.baidu.com/doc/NLP/s/1lg53dryv"/a>}
 * @author meidanlong
 * @date 2023/6/21
 * @version 1.0.0
 */
@Slf4j
@Service
public class WenxinyigeServiceImpl implements IWenxinyigeService {

    private final static String TXT_IMAGE_URL="https://aip.baidubce.com/rpc/2.0/ernievilg/v1/txt2imgv2?access_token=%s";
    private final static String GET_IMAGE_URL="https://aip.baidubce.com/rpc/2.0/ernievilg/v1/getImgv2?access_token=%s";
    private final static String TASK_STATUS_SUCCESS ="SUCCESS";

    @Resource
    private IAccessTokenService accessTokenService;

    public Long txt2img(Txt2ImgDTO req){
        String url = String.format(TXT_IMAGE_URL, accessTokenService.getAccessToken());
        Txt2ImgReqDTO txt2ImgReqDTO = buildTxt2ImgReqDTO(req);
        String response = HttpUtil.doPostJson(url, commonHeaders(), JSON.toJSONString(txt2ImgReqDTO));
        log.info("[IWenxinyigeService#txt2img] - response={}", response);
        if(StringUtil.isEmpty(response)){
            throw new BusinessException("文心一格文生图异常");
        }
        try{
          return JSON.parseObject(response).getJSONObject("data").getLong("primary_task_id");
        }catch (Exception e){
            throw new BusinessException("文心一格文生图结果解析失败");
        }
    }

    public ImageTaskRespDTO getImage(Long taskId){
        String url = String.format(GET_IMAGE_URL, accessTokenService.getAccessToken());
        Map<String, Long> params = Collections.singletonMap("task_id", taskId);
        String response = HttpUtil.doPostJson(url, commonHeaders(), JSON.toJSONString(params));
        log.info("[IWenxinyigeService#getImage] - response={}", response);
        if(StringUtil.isEmpty(response)){
            throw new BusinessException("文心一格获取图片接口异常");
        }
        try{
            return JSON.parseObject(JSON.parseObject(response).getString("data"), ImageTaskRespDTO.class) ;
        }catch (Exception e){
            throw new BusinessException("文心一格获取图片接口解析失败");
        }
    }

    private Txt2ImgReqDTO buildTxt2ImgReqDTO(Txt2ImgDTO txt2ImgDTO) {
        if (txt2ImgDTO == null) {
            return null;
        }
        Txt2ImgReqDTO txt2ImgReqDTO = new Txt2ImgReqDTO();
        txt2ImgReqDTO.setPrompt(txt2ImgDTO.getPrompt());
        txt2ImgReqDTO.setVersion("v2");
        txt2ImgReqDTO.setWidth(txt2ImgDTO.getImageRatio().getWidth());
        txt2ImgReqDTO.setHeight(txt2ImgDTO.getImageRatio().getHeight());
        txt2ImgReqDTO.setImageNum(txt2ImgDTO.getImageNum());
        txt2ImgReqDTO.setImage(txt2ImgDTO.getImage());
        txt2ImgReqDTO.setUrl(txt2ImgDTO.getUrl());
        txt2ImgReqDTO.setChangeDegree(txt2ImgDTO.getChangeDegree());
        return txt2ImgReqDTO;
    }

    private Map commonHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }


    @SneakyThrows
    public static void main(String[] args) {
        WenxinyigeServiceImpl service = new WenxinyigeServiceImpl();

        // 文生图
        Txt2ImgDTO req = new Txt2ImgDTO();
        req.setPrompt("电影光，华丽高光，电影质感，一张逼真的照片，电影场面，在古代市场和繁忙的街道上，一个25岁的亚洲男性，穿着中国古代长袍，黑色大背发，小眼睛，正常的鼻子，小嘴，一张小脸，看起来好奇和坚定，正在观察市场和思考。");
        req.setImageNum(4);
        req.setChangeDegree(7);
        req.setImageRatio(WenxinyigeImageRatioEnum._1024x1024);
        Long taskId = service.txt2img(req);

        TimeUnit.MILLISECONDS.sleep(1000);
        // 轮询
        long curTime = System.currentTimeMillis();
        while(System.currentTimeMillis()-curTime<=3*60*1000){
            // 获取图片进度及结果
            ImageTaskRespDTO imageTask = service.getImage(taskId);
            if(imageTask != null && TASK_STATUS_SUCCESS.equals(imageTask.getTaskStatus())){
                List<String> imageList = new ArrayList<>();
                imageTask.getSubTaskResultList().stream().forEach(subTask -> imageList.addAll(subTask.getFinalImageList().stream().map(ImageDetailRespDTO::getImageUrl).collect(Collectors.toList())));
                log.info("imageList = {}", imageList);
                return;
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }


    }

}
