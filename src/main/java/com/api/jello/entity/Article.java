package com.api.jello.entity;

import com.api.jello.config.Dict;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;


/**
 * @author cc
 */
@Data
public class Article extends BaseEntity {

    /**
     * title
     */
    private String title;

    /**
     * content
     */
    private String content;

    /**
     * view_count
     */
    private Integer viewCount;
    /**
     * view_count
     */
    @Dict(target = "tagDesc",codeType = "TAG")
    private String tag;

    @TableField(exist = false)
    private String tagDesc;

    @TableField(exist = false)
    private Integer countComments;
}
