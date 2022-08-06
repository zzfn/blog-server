package com.zzf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
     * 昵称
     */
    @TableField(exist = false)
    private String nickName;
    /**
     * 头像
     */
    @TableField(exist = false)
    private String avatar;
    /**
     * 地区
     */
    private String address;
}