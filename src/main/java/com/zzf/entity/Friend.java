package com.zzf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 友链
 * @TableName t_friend
 */
@Data
public class Friend extends BaseEntity implements Serializable {
    /**
     * logo
     */
    private String logo;

    /**
     * 链接
     */
    private String url;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否发布
     */
    private Integer isRelease;
}