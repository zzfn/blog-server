package com.zzf.service;

import com.zzf.entity.Discuss;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzf.vo.Tags;

import java.util.List;

/**
 *
 */
public interface DiscussService extends IService<Discuss> {
    List<Discuss> getListById(String id);
}
