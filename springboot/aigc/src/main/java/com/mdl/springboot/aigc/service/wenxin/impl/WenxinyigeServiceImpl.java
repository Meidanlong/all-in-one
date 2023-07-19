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
import org.springframework.util.CollectionUtils;

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

    @Resource
    private IAccessTokenService accessTokenService;

    public Long txt2img(Txt2ImgDTO req){
        String url = String.format(TXT_IMAGE_URL, accessTokenService.getAccessToken());
        Txt2ImgReqDTO txt2ImgReqDTO = buildTxt2ImgReqDTO(req);
        String response = HttpUtil.doPostJson(url, HttpUtil.commonHeaders(), JSON.toJSONString(txt2ImgReqDTO));
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
        String response = HttpUtil.doPostJson(url, HttpUtil.commonHeaders(), JSON.toJSONString(params));
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

}
