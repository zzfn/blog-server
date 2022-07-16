package com.zzf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzf.annotation.IgnoreAuth;
import com.zzf.entity.Friend;
import com.zzf.service.FriendService;
import com.zzf.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 友链(TFriend)表控制层
 *
 * @author makejava
 * @since 2022-07-16 08:39:44
 */
@RestController
@RequestMapping("friend")
public class FriendController {
    /**
     * 服务对象
     */
    @Resource
    private FriendService friendService;

    /**
     * 分页查询所有数据
     *
     * @param page   分页对象
     * @param friend 查询实体
     * @return 所有数据
     */
    @GetMapping
    @IgnoreAuth
    public Object selectAll(Page<Friend> page, Friend friend) {
        return ResultUtil.success(this.friendService.page(page, new QueryWrapper<>(friend)));
    }
    @GetMapping("list")
    @IgnoreAuth
    public Object selectList(@RequestBody Friend friend) {
        return ResultUtil.success(this.friendService.list(new QueryWrapper<>(friend)));
    }
    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @IgnoreAuth
    public Object selectOne(@PathVariable Serializable id) {
        return ResultUtil.success(this.friendService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param friend 实体对象
     * @return 新增结果
     */
    @PostMapping
    public Object insert(@RequestBody Friend friend) {
        return ResultUtil.success(this.friendService.save(friend));
    }
    /**
     * 修改数据
     *
     * @param tFriend 实体对象
     * @return 修改结果
     */
    @PutMapping
    public Object update(@RequestBody Friend tFriend) {
        return ResultUtil.success(this.friendService.updateById(tFriend));
    }
}

