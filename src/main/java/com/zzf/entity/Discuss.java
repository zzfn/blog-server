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
     * 文章id
     */
    private String articleId;
    /**
     * 一級id
     */
    private String parentId;
    /**
     * 回复的评论id
     */
    private String replyId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 地区
     */
    private String address;
}