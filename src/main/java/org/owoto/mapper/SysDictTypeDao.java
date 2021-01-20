package org.owoto.mapper;

import org.owoto.entity.SysDictType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * DICT_TYPE(SysDictType)表数据库访问层
 *
 * @author nanaouyang
 * @since 2020-03-27 21:45:29
 */
@Repository
public interface SysDictTypeDao extends BaseMapper<SysDictType> {
}