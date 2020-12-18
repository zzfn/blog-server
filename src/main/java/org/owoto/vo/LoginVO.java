package org.owoto.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author cc
 * @date 2020/8/10 0:22
 */
@Data
public class LoginVO {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
