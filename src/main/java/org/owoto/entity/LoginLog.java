package org.owoto.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * LOGIN_LOG
 */
@Setter
@Getter
public class LoginLog extends BaseEntity {
    /**
     * 用户id
     */
    private String userId;

    /**
     * ip地址
     */
    private String ip;
}