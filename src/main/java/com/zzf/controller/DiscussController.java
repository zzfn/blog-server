package com.zzf.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzf.annotation.IgnoreAuth;
import com.zzf.entity.Discuss;
import com.zzf.service.DiscussService;
import com.zzf.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 评论表(Discuss)表控制层
 *
 * @author cc
 * @since 2021-06-16 16:45:18
 */
@RestController
@RequestMapping("discuss")
@Slf4j
public class DiscussController {
    /**
     * 服务对象
     */
    @Resource
    private DiscussService discussService;
    @Resource
    private RedisUtil redisUtil;

    @PostMapping("save")
    @IgnoreAuth
    public Object saveOne(@RequestBody Discuss discuss) {
        String ip = IpUtil.getIp();
        String address = (String) redisUtil.hget("address", ip);
        if (StringUtils.isBlank(address)) {
            address = IpUtil.getAddress(ip);
            redisUtil.hset("address", ip, address);
        }
        discuss.setAddress(address);
        if(StringUtils.isBlank(HttpUtil.getUserId())){
            discuss.setCreateBy(ip);
        }
        BotUtil.postMessage(ip+":评论了文章"+discuss.getArticleId()+"内容"+discuss.getContent());
        return ResultUtil.success(this.discussService.save(discuss));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("select")
    @IgnoreAuth
    public Object selectOne(String id) {
        return ResultUtil.success(this.discussService.getListById(id));
    }

}
