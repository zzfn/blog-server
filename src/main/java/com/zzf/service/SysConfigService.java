package com.zzf.service;

import com.zzf.entity.SysConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * T_SYS_CONFIG(TSysConfig)表服务接口
 *
 * @author makejava
 * @since 2020-04-23 15:46:51
 */
public interface SysConfigService extends IService<SysConfig> {
    SysConfig selectOne(String key);
    List<SysConfig> selectList();
}