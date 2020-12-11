package com.api.jello.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @author zzfn
 * @date 2020-12-10 14:49
 */
@Document(indexName = "article", type = "_doc", replicas = 0, shards = 5)
@Data
public class ArticleES {
    @Id
    private String id;
    @Field(type = FieldType.Text, analyzer = "ik_max_world")
    private String title;
    @Field(type = FieldType.Text, analyzer = "ik_max_world")
    private String content;
    @Field(type = FieldType.Integer)
    private Double viewCount;
    @Field(type = FieldType.Integer)
    private Double orderNum;
    @Field(index = false, type = FieldType.Keyword)
    private String tag;
    @Field(index = false, type = FieldType.Date,pattern = "YYYY-MM-DD",format = DateFormat.custom)
    private Date createTime;
    @Field(index = false, type = FieldType.Short)
    private Integer isDelete;
}
