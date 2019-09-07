package com.meidl.elasticsearch.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * es 文档
 */

//InvalidIndexNameException: Invalid index name [blogIndexName], must be lowercase
@Document(indexName = "blogindexname",type = "blogtype")//文档
public class EsBlog implements Serializable {


    private static final long serialVersionUID = -5843042877679596807L;
    @Id
    private String id;//主键
    private String title;
    private String summary;
    private String content;

    protected  EsBlog() {// JPA规范要求：防止直接使用
    }

    public EsBlog(String title, String summary, String content) {
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "EsBlog{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
