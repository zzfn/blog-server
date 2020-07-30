package com.api.jello.service;

import com.api.jello.entity.User;
import java.util.List;

/**
 * USER(TUser)表服务接口
 *
 * @author nanaouyang
 * @since 2020-04-11 22:40:47
 */
public interface UserService {
    String getToken(User user);
}