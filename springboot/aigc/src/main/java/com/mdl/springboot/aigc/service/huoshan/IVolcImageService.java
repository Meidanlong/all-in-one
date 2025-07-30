package com.mdl.springboot.aigc.service.huoshan;

import java.util.List;

/**
 * 火山图像处理接口
 *
 * @author meidanlong
 * @date 2024年07月17日
 * @version: 1.0
 */
public interface IVolcImageService {

    /**
     * 生成图片并换脸
     *
     * @param prompt
     * @param roleFaces
     */
    void generateImageAndSwapFace(String prompt, List<String> roleFaces);

    /**
     * 动漫生图 V1.3
     *
     * @param style
     * @param prompt
     * @return
     */
    String generateAnimeImageV1_3(String style, String prompt);

    /**
     * 通用文生图 V1.4
     *
     * @param prompt
     * @return
     */
    String generateImageV1_4(String prompt);

    /**
     * 人脸融合
     *
     * @param image
     * @param roleFaces
     * @return
     */
    String faceSwap(String image, List<String> roleFaces);

    /**
     * 智能扩图
     *
     * @param prompt
     * @param imgUrl
     * @return
     */
    String outPainting(String prompt, String imgUrl);

    /**
     * 图片增强
     *
     * @param base64
     * @return
     */
    String enhancePhoto(String base64);

    /**
     * 通用2.0-角色特征保持
     * https://www.volcengine.com/docs/6791/1361423
     *
     * @param oriImg
     * @param prompt
     * @return
     */
    String imageEditByCv(String oriImg, String prompt);

    /**
     * 特征提取
     * 图生图3.0-角色特征保持DreamO
     * https://www.volcengine.com/docs/85128/1722713
     *
     * @param oriImg
     * @param prompt
     * @return
     */
    String featureExtractionSubmitByDreamO(String oriImg, String prompt);

    /**
     * 特征提取任务轮询
     * 图生图3.0-角色特征保持DreamO
     * https://www.volcengine.com/docs/85128/1722713
     *
     * @param taskId
     * @return
     */
    String featureExtractionQueryByDreamO(String taskId);
}
