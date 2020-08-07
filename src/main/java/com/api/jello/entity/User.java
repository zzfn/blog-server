package com.api.jello.entity;

import lombok.Data;
/**
 * USER(TUser)实体类
 *
 * @author nanaouyang
 * @since 2020-04-11 22:40:47
 */
@Data
public class User extends BaseEntity {
    /**
    * 用户名
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
     * openid
     */
    private String openid;
    /**
     * role
     */
    private String role;
}