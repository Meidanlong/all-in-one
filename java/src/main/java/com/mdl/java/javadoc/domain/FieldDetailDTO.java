package com.mdl.java.javadoc.domain;

import lombok.Data;

/**
 * @description: 属性信息
 * @author: meidanlong
 * @date: 2023/1/9 17:51
 */
public class FieldDetailDTO {

    private String fieldDesc;

    private String fieldName;

    private String fieldType;

    private String fieldAuthor;

    private String fieldDate;
    /**
     * 需求版本
     * @author meidanlong
     * @date 2023/1/9
     * @version 1.0.0
     * @required
     */
    private String fieldVersion;

}
