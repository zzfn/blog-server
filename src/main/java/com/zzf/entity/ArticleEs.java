package com.zzf.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author zzfn
 * @date 2020-12-10 14:49
 */
@Document(indexName = "blog", replicas = 0, shards = 5)
@Data
@Builder
public class ArticleEs {
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
}
