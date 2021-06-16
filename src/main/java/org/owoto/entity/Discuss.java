package org.owoto.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
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
     * 回复的评论id
     */
    private String replyId;

}