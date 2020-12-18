package org.owoto.entity;

import lombok.Data;

@Data
public class Menu extends BaseEntity {
    private String name;
    private String parentId;
    private String path;
    private String component;
    private String icon;
    private Integer isShow;
}
