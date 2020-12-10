package com.api.jello.entity;

import com.api.jello.config.Dict;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
    @Field(type = FieldType.Double)
    private Double viewCount;
    @Field(type = FieldType.Double)
    private Double orderNum;
    @Field(index = false, type = FieldType.Keyword)
    private String tag;

}
