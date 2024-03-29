package com.zzf.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author chenchen
 */
@Data
public class BaseEntity implements Serializable {

    @TableId(value = "ID",type= IdType.ASSIGN_ID)
    private String id;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT,jdbcType= JdbcType.TIMESTAMP)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE,jdbcType= JdbcType.TIMESTAMP)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer isDelete;
}
