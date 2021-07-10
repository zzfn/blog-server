package com.zzf.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

/**
 * USER(TUser)实体类
 *
 * @author nanaouyang
 * @since 2020-04-11 22:40:47
 */
@Data
public class User extends BaseEntity {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    @JSONField(serialize = false)
    private String password;

    private String nickName;
    /**
     * openid
     */
    private String openid;
    /**
     * roleName
     */
    @TableField(exist = false)
    private String roleName;
    /**
     * roleValue
     */
    @TableField(exist = false)
    private String roleValue;
    /**
     * 头像
     */
    private String avatar;
    @TableField(exist = false)
    private List<Role> roleList;

}