package com.zzf.entity;

import lombok.Data;

/**
 * @author cc
 */
@Data
public class Task extends BaseEntity {
    private String content;
    private Integer status;
}
