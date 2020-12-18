package org.owoto.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

/**
 * COMMENT(TComment)实体类
 *
 * @author nanaouyang
 * @since 2020-04-09 21:09:39
 */
@Data
public class Comment extends BaseEntity   {
    /**
    * 文章id
    */
    private String articleId;
    /**
    * 内容
    */
    private String content;
    /**
    * 用户id
    */
    private String userId;

    @TableField(exist = false)
    private List<Reply> replyList;

}