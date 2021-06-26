package org.owoto.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * LOGIN_LOG
 * @TableName T_LOGIN_LOG
 */
@TableName(value ="T_LOGIN_LOG")
@Data
public class LoginLog implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标识
     */
    private String isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}