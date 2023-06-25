package com.mdl.springboot.aigc.service.wenxin;

import com.mdl.springboot.aigc.domain.wenxin.ImageTaskRespDTO;
import com.mdl.springboot.aigc.domain.wenxin.Txt2ImgDTO;

public interface IWenxinyigeService {

    /**
     * 文生图
     *
     *  {@link <a href="https://cloud.baidu.com/doc/NLP/s/1lg53dryv#%E6%8F%90%E4%BA%A4%E8%AF%B7%E6%B1%82-%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E"/a>}
     * @author meidanlong
     * @date 2023/6/25
     * @version 1.0.0
     * @param req 文生图请求 @required
     * @return Long
     */
    Long txt2img(Txt2ImgDTO req);

    /**
     * 获取图片（进度/结果查询）
     *
     *  {@link <a href="https://cloud.baidu.com/doc/NLP/s/1lg53dryv#%E6%9F%A5%E8%AF%A2%E7%BB%93%E6%9E%9C-%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E"/a>}
     * @author meidanlong
     * @date 2023/6/25
     * @version 1.0.0
     * @param taskId 任务ID @required
     * @return ImageTaskRespDTO
     */
    ImageTaskRespDTO getImage(Long taskId);
}
