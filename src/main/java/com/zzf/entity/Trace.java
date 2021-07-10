package com.zzf.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * TRACE
 * @TableName T_TRACE
 */
@TableName(value ="T_TRACE")
@Data
public class Trace extends BaseEntity {
    /**
     * 内容
     */
    private String content;

    /**
     * 日志类型
     */
    private String type;

    /**
     * ip地址
     */
    private String ip;

    private String userAgent;
}