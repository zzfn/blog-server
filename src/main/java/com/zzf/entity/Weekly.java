package com.zzf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * WEEKLY
 * @TableName t_weekly
 */
@TableName(value ="t_weekly")
@Data
public class Weekly extends BaseEntity implements Serializable {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签
     */
    private String tag;

    /**
     * 排序号
     */
    private Byte orderNum;

    /**
     * 是否发布
     */
    private Boolean isRelease;

}