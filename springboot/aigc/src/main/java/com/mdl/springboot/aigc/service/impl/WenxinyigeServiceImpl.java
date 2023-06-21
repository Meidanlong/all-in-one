package com.mdl.springboot.aigc.service.impl;

import com.alibaba.fastjson.JSON;
import com.mdl.common.domain.BusinessException;
import com.mdl.common.utils.HttpUtil;
import com.mdl.common.utils.StringUtil;
import com.mdl.springboot.aigc.domain.wenxin.AccessTokenDTO;
import com.mdl.springboot.aigc.domain.wenxin.Txt2ImgDTO;
import com.mdl.springboot.aigc.domain.wenxin.Txt2ImgReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
public class WenxinyigeServiceImpl {

    private final static String ACCESS_TOKEN_URL="https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=%s&client_secret=%s";
    private final static String TXT_IMAGE_URL="https://aip.baidubce.com/rpc/2.0/ernievilg/v1/txt2imgv2?access_token=%s";
    private final static String GET_IMAGE_URL="https://aip.baidubce.com/rpc/2.0/ernievilg/v1/getImgv2?access_token=%s";
    private final static String API_KEY = "";
    private final static String SECRET_KEY = "";

    public String getAccessToken(){
        String url = String.format(ACCESS_TOKEN_URL, API_KEY, SECRET_KEY);
        String response = HttpUtil.doPost(url);
        if(StringUtil.isEmpty(response)){
            throw new BusinessException("文心获取accessToken异常");
        }
        log.info("[IWenxinyigeService#getAccessToken] - response={}", response);
        AccessTokenDTO accessToken = JSON.parseObject(response, AccessTokenDTO.class);
        // todo accessToken缓存处理
        return accessToken.getAccessToken();
    }

    public void txt2img(Txt2ImgDTO req){
        String url = String.format(TXT_IMAGE_URL, getAccessToken());
        Txt2ImgReqDTO txt2ImgReqDTO = buildTxt2ImgReqDTO(req);
        String response = HttpUtil.doPostJson(url, commonHeaders(), JSON.toJSONString(txt2ImgReqDTO));
        log.info("[IWenxinyigeService#txt2img] - response={}", response);
    }

    public void getImage(Long taskId){
        String url = String.format(GET_IMAGE_URL, getAccessToken());
        Map<String, Long> params = Collections.singletonMap("task_id", taskId);
        String response = HttpUtil.doPostJson(url, commonHeaders(), JSON.toJSONString(params));
        log.info("[IWenxinyigeService#getImage] - response={}", response);
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


    public static void main(String[] args) {
        WenxinyigeServiceImpl service = new WenxinyigeServiceImpl();

        // 获取accesstoken
//        String accessToken = service.getAccessToken();
//        System.out.println(accessToken);

        // 文生图
//        Txt2ImgDTO req = new Txt2ImgDTO();
//        req.setPrompt("1boy");
//        req.setImageNum(1);
//        req.setChangeDegree(7);
//        req.setImageRatio(WenxinyigeImageRatioEnum._512x512);
//        service.txt2img(req);

        // 获取图片进度及结果
        service.getImage(1L);
    }

}
