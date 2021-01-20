package org.owoto.mapper;

import org.owoto.entity.SysConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * T_SYS_CONFIG(TSysConfig)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-23 15:46:51
 */
@Repository
public interface SysConfigDao extends BaseMapper<SysConfig> {
}