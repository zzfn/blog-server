package org.owoto.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;


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
