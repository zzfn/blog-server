package org.owoto.mapper;

import org.owoto.entity.Role;
import org.owoto.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * USER(TUser)表数据库访问层
 *
 * @author nanaouyang
 * @since 2020-04-11 22:40:47
 */
@Repository
public interface UserRoleDao extends BaseMapper<UserRole> {
    List<Role> getRoles(String userId);

}