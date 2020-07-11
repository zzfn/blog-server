package com.api.jello.dao;

import com.api.jello.entity.SysDictType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DICT_TYPE(SysDictType)表数据库访问层
 *
 * @author nanaouyang
 * @since 2020-03-27 21:45:29
 */
@Repository
public interface SysDictTypeDao extends BaseMapper<SysDictType> {
}