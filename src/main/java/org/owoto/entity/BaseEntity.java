package org.owoto.entity;

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

    @TableId(value = "id",type= IdType.ASSIGN_ID)
    private String id;

    @TableField(fill = FieldFill.INSERT,select = false)
    private String createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE,select = false)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT,jdbcType= JdbcType.TIMESTAMP)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE,jdbcType= JdbcType.TIMESTAMP,update = "now()")
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT,select = false)
    @TableLogic
    private Integer isDelete;
}
