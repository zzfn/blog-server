package com.zzf.entity;

import lombok.Data;

/**
 * 评论表
 * @TableName T_DISCUSS
 */
@Data
public class Discuss extends BaseEntity {
    /**
     * 内容
     */
    private String content;
    /**
     * 备注
     */
    private String remark;
    /**
     * 文章id
     */
    private String articleId;

    /**
     * 回复的评论id
     */
    private String replyId;

}