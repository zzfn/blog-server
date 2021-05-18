package org.owoto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.owoto.config.Dict;
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
@Document(indexName = "article", replicas = 0, shards = 5)
@Data
@Builder
public class ArticleEs {
    @Id
    private String id;
    @Field(type = FieldType.Text, analyzer = "ik_max_world")
    private String title;
    @Field(type = FieldType.Text, analyzer = "ik_max_world")
    private String content;
    @Field(type = FieldType.Integer, name = "view_count")
    private Double viewCount;
    @Field(type = FieldType.Integer, name = "order_num")
    private Double orderNum;
    @Field(type = FieldType.Integer, name = "is_release")
    private Boolean isRelease;
    @Field(index = false, type = FieldType.Keyword)
    @Dict(target = "tagDesc",codeType = "TAG")
    private String tag;
    @Field(index = false, type = FieldType.Keyword, name = "tag_desc")
    private String tagDesc;
    @Field(index = false, type = FieldType.Date, pattern = "YYYY-MM-DD", format = DateFormat.custom)
    private Date createTime;
    @Field(index = false, type = FieldType.Short, name = "is_delete")
    private Integer isDelete;
}
