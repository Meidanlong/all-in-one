package com.mdl.java.javadoc.domain;

import java.util.List;

/**
 * @description: 方法信息
 * @author: meidanlong
 * @date: 2023/1/9 17:51
 */
public class MethodDetailDTO {

    private String methodDesc;

    private String methodName;

    private String methodType;

    private String methodAuthor;

    private String methodDate;
    /**
     * 需求版本
     * @author meidanlong
     * @date 2023/1/9
     * @version 1.0.0
     */
    private String methodVersion;

    private List<CommonPojoDTO> methodParams;

    private CommonPojoDTO methodReturn;

    /**
     * 通用对象
     * @author meidanlong
     * @date 2023/1/10
     * @version 1.0.0
     */
    class CommonPojoDTO {

        private String pojoType;

        private String pojoName;

        private String pojoDesc;

        public String getPojoType() {
            return pojoType;
        }

        public void setPojoType(String pojoType) {
            this.pojoType = pojoType;
        }

        public String getPojoName() {
            return pojoName;
        }

        public void setPojoName(String pojoName) {
            this.pojoName = pojoName;
        }

        public String getPojoDesc() {
            return pojoDesc;
        }

        public void setPojoDesc(String pojoDesc) {
            this.pojoDesc = pojoDesc;
        }
    }
}
