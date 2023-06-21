package com.mdl.springboot.aigc.domain.enums;

/**
 * 文心一格图片比例枚举
 *
 *  {@link <a href="https://cloud.baidu.com/doc/NLP/s/1lg53dryv"/a>}
 * @author meidanlong
 * @date 2023/6/21
 * @version 1.0.0
 */
public enum WenxinyigeImageRatioEnum {

    // v2 版本支持：512x512、640x360、360x640、1024x1024、1280x720、720x1280、2048x2048、2560x1440、1440x2560
    _512x512(512,512),
    _640x360(640,360),
    _360x640(360,640),
    _1024x1024(1024,1024),
    _1280x720(1280,720),
    _720x1280(720,1280),
    _2048x2048(2048,2048),
    _2560x1440(2560,1440),
    _1440x2560(1440,2560),
    ;

    private Integer width;
    private Integer height;

    WenxinyigeImageRatioEnum(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
