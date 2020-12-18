package org.owoto.entity;

import lombok.Data;

/**
 * @author cc
 * @date 2020/8/9 21:14
 */
@Data
public class Role extends BaseEntity {

    private String roleValue;

    private String roleName;
}
