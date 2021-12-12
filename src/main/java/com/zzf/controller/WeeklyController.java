package com.zzf.controller;

import com.zzf.entity.Weekly;
import com.zzf.service.WeeklyService;
import com.zzf.util.ResultUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * WEEKLY(Weekly)表控制层
 *
 * @author makejava
 * @since 2021-12-12 15:31:00
 */
@RestController
@RequestMapping("weekly")
public class WeeklyController {
    /**
     * 服务对象
     */
    @Resource
    private WeeklyService weeklyService;

    /**
     * 分页查询
     *
     * @param weekly 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public Object queryByPage(Weekly weekly, PageRequest pageRequest) {
        return ResultUtil.success(null);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Object queryById(@PathVariable("id") Long id) {
        return ResultUtil.success(null);
    }

    /**
     * 新增数据
     *
     * @param weekly 实体
     * @return 新增结果
     */
    @PostMapping
    public Object add(Weekly weekly) {
        return ResultUtil.success(null);
    }

    /**
     * 编辑数据
     *
     * @param weekly 实体
     * @return 编辑结果
     */
    @PutMapping
    public Object edit(Weekly weekly) {
        return ResultUtil.success(null);
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public Object deleteById(Long id) {
        return ResultUtil.success(null);
    }

}

