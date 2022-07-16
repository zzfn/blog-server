package com.zzf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzf.entity.Friend;
import com.zzf.service.FriendService;
import com.zzf.mapper.FriendMapper;
import org.springframework.stereotype.Service;

/**
* @author cc
* @description 针对表【t_friend(友链)】的数据库操作Service实现
* @createDate 2022-07-16 08:38:55
*/
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend>
    implements FriendService{

}




