package com.api.jello.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * @program: jello
 * @author: nanaouyang
 * @create: 2020/03/23 16:50
 */
@Data
public class BaseEntity {

    @TableId(value = "id",type= IdType.ASSIGN_UUID)
    private String id;

    @TableField(select = false)
    private String createBy;

    @TableField(select = false)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT,jdbcType= JdbcType.TIMESTAMP)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE,jdbcType= JdbcType.TIMESTAMP,select = false)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT,select = false)
    @TableLogic
    private Integer isDelete;
}
