package com.zzf.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * LOG_SYSTEM
 * @author cc
 */
@Data
public class LogUser extends BaseEntity{

    /**
     * 类型
     */
    private String type;

    /**
     * 值
     */
    private Integer value;


    /**
     * 时间
     */
    private LocalDateTime time;

}