package com.zzf.entity;

import lombok.Data;

/**
 * @author c.chen
 */
@Data
public class Menu extends BaseEntity {
    private String name;

    private String parentId;

    private String path;

    private String component;

    private Integer orderNum;

    private String icon;

    private Boolean isShow;
}
