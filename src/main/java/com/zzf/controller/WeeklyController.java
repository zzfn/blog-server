package com.zzf.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzf.annotation.IgnoreAuth;
import com.zzf.entity.Weekly;
import com.zzf.service.WeeklyService;
import com.zzf.util.ResultUtil;
import com.zzf.vo.PageVO;
import org.springframework.data.domain.PageRequest;
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
     * @param pageVO      筛选条件
     * @return 查询结果
     */
    @GetMapping
    @IgnoreAuth
    public Object queryByPage(PageVO pageVO) {
        IPage<Weekly> page = new Page<>(pageVO.getCurrent(), pageVO.getPageSize());
        IPage<Weekly> pageList = weeklyService.page(page);
        return ResultUtil.success(pageList);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @IgnoreAuth
    public Object queryById(@PathVariable("id") Long id) {
        return ResultUtil.success(weeklyService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param weekly 实体
     * @return 新增结果
     */
    @PostMapping
    @IgnoreAuth
    public Object add(@RequestBody Weekly weekly) {
        return ResultUtil.success(weeklyService.saveOrUpdate(weekly));
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

