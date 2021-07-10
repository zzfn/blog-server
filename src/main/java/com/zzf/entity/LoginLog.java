package com.zzf.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * LOGIN_LOG
 */
@Setter
@Getter
public class LoginLog extends BaseEntity {
    /**
     * 用户id
     */
    private String userId;

    /**
     * ip地址
     */
    private String ip;
}