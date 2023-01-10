package com.mdl.java.javadoc.domain;

import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/1/9 15:04
 */
public class ModelClassDocVO {

    private String modelTableName;

    private String modelClassName;

    private String modelCommentText;

    private List<FieldEntry> fieldEntryList;


    public String getModelTableName() {
        return modelTableName;
    }

    public void setModelTableName(String modelTableName) {
        this.modelTableName = modelTableName;
    }

    public String getModelClassName() {
        return modelClassName;
    }

    public void setModelClassName(String modelClassName) {
        this.modelClassName = modelClassName;
    }

    public String getModelCommentText() {
        return modelCommentText;
    }

    public void setModelCommentText(String modelCommentText) {
        this.modelCommentText = modelCommentText;
    }

    public List<FieldEntry> getFildEntryList() {
        return fieldEntryList;
    }

    public void setFildEntryList(List<FieldEntry> fieldEntryList) {
        this.fieldEntryList = fieldEntryList;
    }

    @Override
    public String toString() {
        return "ModelClassDocVO{" +
                "modelClassName='" + modelClassName + '\'' +
                ", modelCommentText='" + modelCommentText + '\'' +
                ", fildEntryList=" + fieldEntryList +
                '}';
    }
}
