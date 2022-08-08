package com.zzf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * T_RESOURCE
 *
 * @TableName t_resource
 */
@Data
public class Device extends BaseEntity implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源类型
     */
    private String type;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 备注
     */
    private String remark;

    /**
     * 到期时间
     */
    private String panel;

    /**
     * 到期时间
     */
    private Date dueDate;

}