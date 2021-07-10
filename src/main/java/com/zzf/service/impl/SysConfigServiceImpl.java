package com.zzf.service.impl;

import com.zzf.mapper.SysConfigDao;
import com.zzf.entity.SysConfig;
import com.zzf.service.SysConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * T_SYS_CONFIG(TSysConfig)表服务实现类
 *
 * @author makejava
 * @since 2020-04-23 15:46:51
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfig> implements SysConfigService {
    @Autowired
    SysConfigDao sysConfigDao;

    @Override
    public SysConfig selectOne(String key) {
        return sysConfigDao.selectOne(new QueryWrapper<SysConfig>().eq("KEY", key));
    }

    @Override
    public List<SysConfig> selectList() {
        return sysConfigDao.selectList(new QueryWrapper<SysConfig>().eq("key", "endpoint").or().eq("accessKeyId", "accessKeyId").or().eq("accessKeySecret", "accessKeySecret").or().eq("bucketName", "bucketName"));
    }
}