package com.zzf.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzf.annotation.IgnoreAuth;
import com.zzf.entity.Discuss;
import com.zzf.service.DiscussService;
import com.zzf.util.IpUtil;
import com.zzf.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("save")
    @IgnoreAuth
    public Object saveOne(@RequestBody Discuss discuss) {
        discuss.setAddress(IpUtil.getAddress("221.225.151.97"));
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
        QueryWrapper<Discuss> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ARTICLE_ID", id).orderByDesc("CREATE_TIME");
        return ResultUtil.success(this.discussService.list(queryWrapper));
    }

}
