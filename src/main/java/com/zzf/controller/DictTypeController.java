package com.zzf.controller;

import com.zzf.mapper.SysDictTypeDao;
import com.zzf.entity.SysDictType;
import com.zzf.util.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * DICT_TYPE(SysDictType)表控制层
 *
 * @author nanaouyang
 * @since 2020-03-27 21:45:29
 */
@RestController
@RequestMapping("sysDictType")
public class DictTypeController {


    @Autowired
    private SysDictTypeDao sysDictTypeDao;

    /**
     * 根据code获取字典类型详情
     * @param code
     * @return
     */
    @GetMapping("getDictTypeOne")
    public Object getDictTypeOne(@RequestParam String code) {
        QueryWrapper<SysDictType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        return ResultUtil.success(sysDictTypeDao.selectOne(queryWrapper));
    }

    /**
     * 字典类型列表
     * @return
     */
    @GetMapping("listDictType")
    public Object listDictType() {
        return ResultUtil.success(sysDictTypeDao.selectList(new QueryWrapper<SysDictType>().orderByDesc("CREATE_TIME")));
    }

    /**
     * 新增或修改字典类型
     * @param sysDictType
     * @return
     */
    @PostMapping("saveDictType")
    @PreAuthorize("hasRole('ADMIN')")
    public Object saveDictType(@RequestBody SysDictType sysDictType) {
        sysDictType.setUpdateTime(null);
        if (null == sysDictType.getId() || null == sysDictTypeDao.selectById(sysDictType.getId())) {
            return ResultUtil.success(sysDictTypeDao.insert(sysDictType));
        } else {
            return ResultUtil.success(sysDictTypeDao.updateById(sysDictType));
        }
    }

    /**
     * 根据code删除一个字典类型
     * @param sysDictType
     * @return
     */
    @DeleteMapping("removeDictType")
    @PreAuthorize("hasRole('ADMIN')")
    public Object removeDictType(@RequestBody SysDictType sysDictType) {
        return ResultUtil.success(sysDictTypeDao.deleteById(sysDictType.getId()));
    }
}