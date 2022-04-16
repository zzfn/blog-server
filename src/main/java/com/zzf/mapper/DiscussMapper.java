package com.zzf.mapper;

import com.zzf.entity.Discuss;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Entity Discuss
 */
public interface DiscussMapper extends BaseMapper<Discuss> {
    List<Discuss> queryAllById(String id);
}




