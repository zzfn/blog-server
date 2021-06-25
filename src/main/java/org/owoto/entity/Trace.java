package org.owoto.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
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

}