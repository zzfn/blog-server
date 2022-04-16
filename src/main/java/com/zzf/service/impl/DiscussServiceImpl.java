package com.zzf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzf.entity.Discuss;
import com.zzf.mapper.ArticleDao;
import com.zzf.mapper.DiscussMapper;
import com.zzf.service.DiscussService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author cc
 */
@Service
public class DiscussServiceImpl extends ServiceImpl<DiscussMapper, Discuss>
        implements DiscussService {
    @Resource
    DiscussMapper discussMapper;

    @Override
    public List<Discuss> getListById(String id) {
        return discussMapper.queryAllById(id);
    }
}




