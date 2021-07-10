package com.zzf.entity;

import lombok.Data;
/**
 * T_SYS_CONFIG(TSysConfig)实体类
 *
 * @author makejava
 * @since 2020-04-23 15:46:51
 */
@Data
public class SysConfig extends BaseEntity {
    /**
    * 值
    */
    private String value;
    /**
    * 键
    */
    private String field;
    private String name;
}