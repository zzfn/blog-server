package com.api.jello.service.impl;

import com.api.jello.entity.User;
import com.api.jello.dao.UserDao;
import com.api.jello.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * USER(TUser)表服务实现类
 *
 * @author nanaouyang
 * @since 2020-04-11 22:40:47
 */
@Service("tUserService")
public class UserServiceImpl implements UserService {

    @Override
    public String getToken(User user) {
        return null;
    }
}