package com.api.jello.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author zzfn
 * @date 2020-12-10 14:49
 */
@Document(indexName = "ec", type = "product", replicas = 0, shards = 5)
@Data
public class ArticleES {
    @Id
    private Long id;
    @Field(type = FieldType.Text, analyzer = "ik_max_world")
    private String name;
    @Field(type = FieldType.Keyword)
    private String category;
    @Field(type = FieldType.Double)
    private Double price;
    @Field(index = false, type = FieldType.Keyword)
    private String images;
    private String body;
}
