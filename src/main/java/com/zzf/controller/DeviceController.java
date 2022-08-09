package com.zzf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzf.entity.Device;
import com.zzf.service.DeviceService;
import com.zzf.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 评论表(Discuss)表控制层
 *
 * @author cc
 * @since 2021-06-16 16:45:18
 */
@RestController
@RequestMapping("device")
@Slf4j
public class DeviceController {
    /**
     * 服务对象
     */
    @Resource
    private DeviceService deviceService;

    @PostMapping("save")
    @PreAuthorize("hasRole('ADMIN')")
    public Object saveOne(@RequestBody Device resource) {
        return ResultUtil.success(this.deviceService.saveOrUpdate(resource));
    }

    /**
     * 通过主键查询单条数据
     *
     * @return 单条数据
     */
    @GetMapping("list")
    @PreAuthorize("hasRole('ADMIN')")
    public Object selectOne(Page<Device> page) {
        return ResultUtil.success(this.deviceService.page(page));
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Object deviceOne(@PathVariable("id") String id) {
        return ResultUtil.success(this.deviceService.getById(id));
    }
}
