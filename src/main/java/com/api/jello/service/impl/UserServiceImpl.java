package com.api.jello.service.impl;

import com.api.jello.dao.UserDao;
import com.api.jello.dao.UserRoleDao;
import com.api.jello.entity.JwtUser;
import com.api.jello.entity.Role;
import com.api.jello.entity.User;
import com.api.jello.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * USER(TUser)表服务实现类
 *
 * @author nanaouyang
 * @since 2020-04-11 22:40:47
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    UserRoleDao userRoleDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userDao.selectOne(new QueryWrapper<User>().eq("username", username));
        if(null==user){
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }else {
            JwtUser jwtUser=new JwtUser();
            jwtUser.setUsername(user.getUsername());
            jwtUser.setPassword(user.getPassword());
            List<GrantedAuthority> authorities = new ArrayList<>();
            List<Role> roles =  userRoleDao.getRoles(user.getId());
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getRoleValue()));
            }
            jwtUser.setAuthorities(authorities);
            return jwtUser;
        }

    }
}