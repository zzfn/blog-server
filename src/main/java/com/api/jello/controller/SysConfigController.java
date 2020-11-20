package com.api.jello.controller;

import com.api.jello.dao.SysConfigDao;
import com.api.jello.entity.SysConfig;
import com.api.jello.util.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author chenc
 * @since 2020-04-23 15:46:51
 */
@RestController
@RequestMapping("sysConfig")
@Api(tags = "系统管理")
public class SysConfigController {
    @Autowired
    SysConfigDao sysConfigDao;
    @PostMapping("saveSysConfig")
    public Object saveSysConfig(@RequestBody SysConfig sysConfig) {
        return ResultUtil.success(sysConfigDao.insert(sysConfig));
    }
    @GetMapping("selectSysConfig")
    public Object selectSysConfig(String field) {
        return ResultUtil.success(sysConfigDao.selectOne(new QueryWrapper<SysConfig>().eq("FIELD",field)));
    }
    @GetMapping("listSysConfig")
    public Object listSysConfig() {
        return ResultUtil.success(sysConfigDao.selectList(null));
    }
}