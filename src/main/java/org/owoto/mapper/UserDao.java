package org.owoto.mapper;

import io.lettuce.core.dynamic.annotation.Param;
import org.owoto.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.owoto.vo.Tags;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * USER(TUser)表数据库访问层
 *
 * @author nanaouyang
 * @since 2020-04-11 22:40:47
 */
@Repository
public interface UserDao extends BaseMapper<User> {
    User getUser(@Param("id") String id);
}