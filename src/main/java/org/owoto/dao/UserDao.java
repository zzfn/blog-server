package org.owoto.dao;

import org.owoto.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * USER(TUser)表数据库访问层
 *
 * @author nanaouyang
 * @since 2020-04-11 22:40:47
 */
@Repository
public interface UserDao extends BaseMapper<User> {


}