package com.api.jello.dao;

import com.api.jello.entity.SysDict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DICT(SysDict)表数据库访问层
 *
 * @author nanaouyang
 * @since 2020-03-27 20:34:18
 */
@Repository
public interface SysDictDao extends BaseMapper<SysDict> {


}