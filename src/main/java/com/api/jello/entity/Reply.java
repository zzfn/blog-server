package com.api.jello.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
/**
 * REPLY(TReply)实体类
 *
 * @author nanaouyang
 * @since 2020-04-12 13:39:56
 */
@Data
public class Reply extends BaseEntity{
    /**
    * 回复内容
    */
    private String content;
    /**
    * 评论id
    */
    private String commentId;
    /**
    * 用户id
    */
    private String userId;

}