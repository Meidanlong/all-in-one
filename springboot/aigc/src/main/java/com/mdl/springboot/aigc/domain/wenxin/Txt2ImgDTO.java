package com.mdl.springboot.aigc.domain.wenxin;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.mdl.common.domain.BaseObject;
import com.mdl.springboot.aigc.domain.enums.WenxinyigeImageRatioEnum;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 文生图请求对象
 *
 *  {@link <a href="https://cloud.baidu.com/doc/NLP/s/1lg53dryv"/a>}
 * @author meidanlong
 * @date 2023/6/21
 * @version 1.0.0
 */
@Data
@JSONType(naming = PropertyNamingStrategy.SnakeCase)
public class Txt2ImgDTO extends BaseObject {

    /**
     * 生图的文本描述。仅支持中文、日常标点符号。不支持英文，特殊符号，限制 200 字
     */
    private String prompt;
    /**
     * 图片比例
     */
    @JSONField(serialize = false)
    private WenxinyigeImageRatioEnum ImageRatio;
    /**
     * 生成图片数量，默认一张，支持生成 1-8 张
     */
    @Max(8)
    @Min(1)
    private Integer imageNum;
    /**
     * 参考图，需 base64 编码，大小不超过 10M，最短边至少 15px，最长边最大 8192px，支持jpg/jpeg/png/bmp 格式。
     * 优先级：image > url > pdf_file，当image 字段存在时，url、pdf_file 字段失效
     */
    private String image;
    /**
     * 参考图完整 url，url 长度不超过 1024 字节，url 对应的图片需 base64 编码，大小不超过 10M，最短边至少 15px，最长边最大8192px，支持 jpg/jpeg/png/bmp 格式。
     * 优先级：image > url > pdf_file，当image 字段存在时，url 字段失效请注意关闭 URL 防盗链
     */
    private String url;
    /**
     * 参考图影响因子，支持 1-10 内；数值越大参考图影响越大
     */
    @Max(10)
    @Min(1)
    private Integer changeDegree;
}
