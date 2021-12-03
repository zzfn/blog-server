package com.zzf.mapper;

import com.zzf.entity.LogUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author cc
* @description 针对表【t_log_user(LOG_SYSTEM)】的数据库操作Mapper
* @createDate 2021-11-15 23:30:46
* @Entity com.zzf.entity.LogUser
*/
@Repository
public interface LogUserMapper extends BaseMapper<LogUser> {
    List<LogUser> visitorAnalysis();
}




