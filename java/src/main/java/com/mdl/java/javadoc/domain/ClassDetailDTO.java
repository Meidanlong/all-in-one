package com.mdl.java.javadoc.domain;

import java.util.List;

/**
 * @description: 类信息
 * @author: meidanlong
 * @date: 2023/1/9 17:51
 */
public class ClassDetailDTO {

    private String description;

    private String author;

    private String date;

    private List<MethodDetailDTO> methods;

    private List<FieldDetailDTO> fields;

}
