package com.mdl.java.javadoc.domain;

import java.util.List;

/**
 * @description: 类注释
 * @author: meidanlong
 * @date: 2023/1/9 15:10
 */
public class FieldEntry {

    /**
     * 参数名
     */
    private String fName;
    /**
     * 类型
     * @version: 1.0
     */
    private String fType;
    /**
     * 说明
     */
    private String fExplain;

    public FieldEntry(String fName, String fType, String fExplain) {
        super();
        this.fName = fName;
        this.fType = fType;
        this.fExplain = fExplain;
    }

    /**
     * 重写toString方法
     * @author meidanlong
     * @date 2023/1/10
     * @version 1.0.0
     * @param
     * @return String
     */
    @Override
    public String toString() {
        return "Entry{" +
                "fName='" + fName + '\'' +
                ", fType='" + fType + '\'' +
                ", fExplain='" + fExplain + '\'' +
                '}';
    }

    public String getfName() {
        return fName;
    }

    /**
     * set方法
     *
     * @see
     *  {@link <a href=""/a>}
     * @deprecated
     * @author meidanlong
     * @date 2023/1/10
     * @version 1.0.0
     * @param fName fieldDesc @required
     * @return void
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }

    public String getfExplain() {
        return fExplain;
    }

    public void setfExplain(String fExplain) {
        this.fExplain = fExplain;
    }

}
