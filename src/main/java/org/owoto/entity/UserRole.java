package org.owoto.entity;

import lombok.Data;

/**
 * @author cc
 * @date 2020/8/9 21:14
 */
@Data
public class UserRole extends BaseEntity {

    private String userId;

    private String roleId;
}
