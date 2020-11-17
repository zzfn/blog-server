package com.api.jello.entity;

import lombok.Data;

/**
 * @author zzf
 */
@Data
public class Views extends BaseEntity {
    private String id;

    private String articleId;

    private Integer count;
}
