package com.zzf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzf.entity.LogUser;
import com.zzf.service.LogUserService;
import com.zzf.mapper.LogUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cc
 * @description 针对表【t_log_user(LOG_SYSTEM)】的数据库操作Service实现
 * @createDate 2021-11-15 23:30:46
 */
@Service
public class LogUserServiceImpl extends ServiceImpl<LogUserMapper, LogUser>
        implements LogUserService {
    @Resource
    LogUserMapper logUserMapper;

    @Override
    public List<LogUser> visitorAnalysis() {
        return logUserMapper.visitorAnalysis();
    }
}




