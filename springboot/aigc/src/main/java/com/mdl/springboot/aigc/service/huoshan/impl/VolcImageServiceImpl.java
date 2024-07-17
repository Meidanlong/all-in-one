package com.mdl.springboot.aigc.service.huoshan.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mdl.common.domain.BusinessException;
import com.mdl.springboot.aigc.service.huoshan.IVolcImageService;
import com.volcengine.service.visual.IVisualService;
import com.volcengine.service.visual.impl.VisualServiceImpl;
import com.volcengine.service.visual.model.request.VisualFaceSwapV2Request;
import com.volcengine.service.visual.model.response.VisualFaceSwapV2Response;
import com.volcengine.service.visual.model.response.VisualHighAesSmartDrawingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 火山图片处理服务实现
 *
 * @author meidanlong
 * @date 2024年07月17日
 * @version: 1.0
 */
@Service
public class VolcImageServiceImpl implements IVolcImageService {

    private final static String ANIME_PROMPT_FORMAT = "%s, %s";

    @Value("volc_ak")
    private String volcAk;
    @Value("volc_sk")
    private String volcSk;


    public static void main(String[] args) {
        String prompt = "A young woman with long red curly hair, wearing casual home clothes with a shiny jacket, comfortable sweatpants, and flat slippers, sits at the dressing table, holding makeup tools and meticulously applying concealer while adjusting beauty filters.";
//        String roleAFace = "https://ts1.cn.mm.bing.net/th/id/R-C.301756a216f6dfd7fe0c32f5853383d6?rik=mtPhRcVGwG%2bTpA&riu=http%3a%2f%2fpic3.nipic.com%2f20090624%2f2896835_221801012_2.jpg&ehk=GbYy8yUcdyKNn4r%2bdWuaHECPuw5LaiIsJamyo%2bmeEMI%3d&risl=&pid=ImgRaw&r=0";
        String roleAFace = "https://ts1.cn.mm.bing.net/th/id/R-C.c2932d6db8ff8f3b91e437ee0b6cc789?rik=z9EFf8Gr7DdObw&riu=http%3a%2f%2fwww.yzwhcm.com%2fuFile%2f4708%2fproduct%2f2012824192042200.jpg&ehk=7NxOExcVLi0eVFJUWvWG9zUWp8VuHa8XiOBn318UkVs%3d&risl=&pid=ImgRaw&r=0";
        VolcImageServiceImpl volcImageService = new VolcImageServiceImpl();
//        volcImageService.generateAnimeImageV1_3("anime, ancient Chinese", "1 girl");
//        volcImageService.generateImageV1_4("1 girl");
        List<String> roleFaces = new ArrayList<>(Collections.singletonList(roleAFace));
        String modelImage = "https://p26-aiop-sign.byteimg.com/tos-cn-i-vuqhorh59i/2024071714240208A2397ED4CE13948863/output-image-0~tplv-vuqhorh59i-image.jpeg?rk3s=7f9e702d&x-expires=1721283849&x-signature=LJIP7o%2FSpUbwjYmgnBHXWp62nT4%3D";
        String base64 = volcImageService.faceSwap(modelImage, roleFaces);
        System.out.println(base64);
//        volcImageService.generateImageAndSwapFace(prompt, roleFaces);
    }

    public void generateImageAndSwapFace(String prompt, List<String> roleFaces) {
        String image = generateAnimeImageV1_3("anime, ancient Chinese", prompt);
//        String image = generateImageV1_4(prompt);
        System.out.println(image);
        String base64 = faceSwap(image, roleFaces);
        System.out.println(base64);
//        File file = new File("~/Desktop/swap_face_image.png");
//        FileUtil.saveBase64Image(base64, "");
    }

    /**
     * 高美感动漫v1.3-文生图/图生图
     * <p>
     * 示例：{"code":10000,"data":{"algorithm_base_resp":{"status_code":0,"status_message":"Success"},"binary_data_base64":[],"image_urls":["https://p9-aiop-sign.byteimg.com/tos-cn-i-vuqhorh59i/202407171443243EF5E13C8B998699459C/output-image-0~tplv-vuqhorh59i-image.jpeg?rk3s=7f9e702d&x-expires=1721285012&x-signature=oG4ntyUHDb8T%2FSBhtNzMhqewxso%3D"]},"message":"Success","request_id":"202407171443243EF5E13C8B998699459C","time_elapsed":"7.276531665s"}
     *
     * @param style
     * @param prompt
     * @return
     */
    public String generateAnimeImageV1_3(String style, String prompt) {
        IVisualService visualService = buildIVisualService();
        JSONObject req = new JSONObject();
        req.put("req_key", "high_aes");
        req.put("prompt", String.format(ANIME_PROMPT_FORMAT, style, prompt));
        req.put("model_version", "anime_v1.3.1");
        req.put("width", 768);
        req.put("height", 432);
        req.put("return_url", true);
        //图生图必选该字段
//        ArrayList<String> binaryDataBase64 = new ArrayList<String>();
//        binaryDataBase64.add("");
//        req.put("binary_data_base64",binaryDataBase64);
        try {
            VisualHighAesSmartDrawingResponse response = visualService.visualHighAesSmartDrawing(req);
            System.out.println(JSON.toJSONString(response));
            return response.getData().getImageUrls().get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("火山V1.3动漫生图失败");
        }
    }

    /**
     * 高美感通用V1.4-文生图
     * <p>
     * 示例：{"code":10000,"data":{"algorithm_base_resp":{"status_code":0,"status_message":"Success"},"binary_data_base64":[],"image_urls":["https://p26-aiop-sign.byteimg.com/tos-cn-i-vuqhorh59i/2024071714240208A2397ED4CE13948863/output-image-0~tplv-vuqhorh59i-image.jpeg?rk3s=7f9e702d&x-expires=1721283849&x-signature=LJIP7o%2FSpUbwjYmgnBHXWp62nT4%3D"]},"message":"Success","request_id":"2024071714240208A2397ED4CE13948863","time_elapsed":"7.747217581s"}
     *
     * @param prompt
     * @return
     */
    public String generateImageV1_4(String prompt) {
        IVisualService visualService = buildIVisualService();
        JSONObject req = new JSONObject();
        req.put("req_key", "high_aes_general_v14");
        req.put("prompt", prompt);
        req.put("model_version", "general_v1.4");
        req.put("width", 768);
        req.put("height", 432);
        req.put("return_url", true);
        try {
            VisualHighAesSmartDrawingResponse response = visualService.visualHighAesSmartDrawing(req);
            System.out.println(JSON.toJSONString(response));
            return response.getData().getImageUrls().get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("火山V1.4生图失败");
        }
    }

    /**
     * 人像融合
     * https://www.volcengine.com/docs/6791/1130928
     *
     * @param image
     * @param roleFaces
     * @return
     */
    public String faceSwap(String image, List<String> roleFaces) {
        if (!CollectionUtils.isEmpty(roleFaces) && roleFaces.size() > 3) {
            throw new BusinessException("最多上传3张换脸图");
        }
        IVisualService visualService = buildIVisualService();
        VisualFaceSwapV2Request req = new VisualFaceSwapV2Request();
        //注意 3.0版本传faceswap || 3.3版本传face_swap3_3
        req.setReqKey("face_swap3_3");
        ArrayList<String> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roleFaces)) {
            list.addAll(roleFaces);
        }
        list.add(image);
        req.setImageUrls(list);
        // 获取人脸位置的方式，支持以下三种模式
        //l2r:根据人脸中心点从左往右的序号获取
        //t2b:根据人脸中心点从上往下的序号获取
        //area:根据人脸面积从大到小的序号获取（默认）
        req.setFaceType("area");
        ArrayList<VisualFaceSwapV2Request.MergeInfos> mergeInfosList = new ArrayList<>();
        VisualFaceSwapV2Request.MergeInfos mergeInfos = new VisualFaceSwapV2Request.MergeInfos();
        mergeInfos.setLocation(1);
        mergeInfos.setTemplate_location(1);
        mergeInfosList.add(mergeInfos);
        req.setMergeInfos(mergeInfosList);
        req.setDoRisk(false);
        req.setSourceSimilarity("1");
        try {
            VisualFaceSwapV2Response response = visualService.faceSwapV2(req);
            System.out.println(JSON.toJSONString(response));
            return response.getData().getBinaryDataBase64().get(0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("火山人脸融合失败");
        }
    }

    private IVisualService buildIVisualService() {
        IVisualService visualService = VisualServiceImpl.getInstance();
        visualService.setAccessKey(volcAk);
        visualService.setSecretKey(volcSk);
        return visualService;
    }
}
