package com.zzf.controller;

import com.zzf.mapper.SysDictDao;
import com.zzf.entity.SysDict;
import com.zzf.service.SysDictService;
import com.zzf.util.ResultUtil;
import com.zzf.vo.RequestVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * DICT(SysDict)表控制层
 *
 * @author nanaouyang
 * @since 2020-03-27 20:34:20
 */
@RestController
@RequestMapping("sysDict")
@Slf4j
public class DictController {
    @Autowired
    SysDictDao sysDictDao;
    @Autowired
    SysDictService sysDictService;

    /**
     * 刷新缓存
     *
     * @return null
     */
    @GetMapping("clearCache")
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "dict", allEntries = true)
    public Object clearCache() {
        return ResultUtil.success("成功");
    }

    /**
     * 新增某个字典
     *
     * @param sysDict
     * @return
     */
    @PostMapping("saveDict")
    public Object saveDict(@RequestBody SysDict sysDict) {
        return ResultUtil.success(sysDictService.saveDict(sysDict));
    }

    /**
     * 根据字典code获取字典列表
     *
     * @param code
     * @return
     */
    @GetMapping("getDict")
    public Object getDict(String code) {
        return ResultUtil.success(sysDictService.getDict(code));
    }

    /**
     * 获取某个字值详情
     *
     * @param code
     * @return
     */
    @GetMapping("getDictOne")
    public Object getDictOne(@RequestParam String code) {
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        return ResultUtil.success(sysDictDao.selectOne(queryWrapper));
    }

    /**
     * 根据code删除一个字典值
     *
     * @param requestVo
     * @return
     */
    @DeleteMapping("removeDict")
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "dict", allEntries = true)
    public Object removeDictType(@RequestBody RequestVO requestVo) {
        return ResultUtil.success(sysDictDao.deleteById(requestVo.getId()));
    }
}