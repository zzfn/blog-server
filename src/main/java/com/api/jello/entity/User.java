package com.api.jello.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * USER(TUser)实体类
 *
 * @author nanaouyang
 * @since 2020-04-11 22:40:47
 */
@Data
public class User extends BaseEntity implements UserDetails {
    /**
    * 用户名
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
     * openid
     */
    private String openid;
    /**
     * role
     */
    private String roleId;

    @TableField(exist = false)
    private List<Role> roleList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> roles = this.getRoleList();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleValue()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}