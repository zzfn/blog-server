package com.zzf.entity;

import lombok.Data;


/**
 * @author zzf
 */
@Data
public class Message extends BaseEntity {

    private String content;

    private Long userId;

    private Integer linkId;

    private Integer replyId;

}
