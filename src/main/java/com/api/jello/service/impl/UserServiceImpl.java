package com.api.jello.service.impl;

import com.api.jello.entity.User;
import com.api.jello.dao.UserDao;
import com.api.jello.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * USER(TUser)表服务实现类
 *
 * @author nanaouyang
 * @since 2020-04-11 22:40:47
 */
@Service("tUserService")
public class UserServiceImpl implements UserService  {
    @Autowired
    UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userDao.selectOne(new QueryWrapper<User>().eq("username",username));
        return user;
    }
    @Override
    public String getToken(User user) {
        return null;
    }
}