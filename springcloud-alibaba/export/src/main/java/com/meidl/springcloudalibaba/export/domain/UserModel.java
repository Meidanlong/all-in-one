package com.meidl.springcloudalibaba.export.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: meidanlong
 * @date: 2022/7/7 09:31
 */
@Data
public class UserModel implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String userName;
}
