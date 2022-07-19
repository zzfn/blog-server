package com.zzf.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author zzfn
 * @date 2020-12-10 14:49
 */
@Document(indexName = "log-performance")
@Data
@Builder
public class LogEs {
    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "ik_max_world", searchAnalyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_world", searchAnalyzer = "ik_smart")
    private String content;

    @Field(type = FieldType.Text, name = "tag_desc", analyzer = "ik_max_world", searchAnalyzer = "ik_smart")
    private String tagDesc;

    @Field(index = false, type = FieldType.Short, name = "is_release")
    private Short isRelease;

    @Field(index = false, type = FieldType.Short, name = "is_delete")
    private Short isDelete;

    /**
     * 埋点类型
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_world", searchAnalyzer = "ik_smart")
    private String name;
    /**
     * 值
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_world", searchAnalyzer = "ik_smart")
    private Double value;
    /**
     * 浏览器类型
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_world", searchAnalyzer = "ik_smart")
    private String browser;
    /**
     * 浏览器版本
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_world", searchAnalyzer = "ik_smart")
    private String browserVersion;
    /**
     * ip地址
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_world", searchAnalyzer = "ik_smart")
    private String ip;

    @Field(type = FieldType.Text, analyzer = "ik_max_world", searchAnalyzer = "ik_smart")
    private String os;

    @Field(type = FieldType.Text, analyzer = "ik_max_world", searchAnalyzer = "ik_smart")
    private String osVersion;

    @Field(type = FieldType.Date, analyzer = "ik_max_world", searchAnalyzer = "ik_smart")
    private Date time;

    @Field(type = FieldType.Text, analyzer = "ik_max_world", searchAnalyzer = "ik_smart")
    private String url;

    @Field(type = FieldType.Text, analyzer = "ik_max_world", searchAnalyzer = "ik_smart")
    private String visitorId;

    @Field(type = FieldType.Text, analyzer = "ik_max_world", searchAnalyzer = "ik_smart")
    private String referrer;
}
