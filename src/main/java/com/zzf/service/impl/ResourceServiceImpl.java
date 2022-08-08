package com.zzf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzf.entity.Resource;
import com.zzf.service.ResourceService;
import com.zzf.mapper.ResourceMapper;
import org.springframework.stereotype.Service;

/**
 * @author cc
 * @description 针对表【t_resource(T_RESOURCE)】的数据库操作Service实现
 * @createDate 2022-08-08 20:30:49
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource>
        implements ResourceService {

}




