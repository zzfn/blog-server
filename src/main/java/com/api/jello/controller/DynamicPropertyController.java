package com.api.jello.controller;

import com.api.jello.dao.DynamicPropertyDao;
import com.api.jello.entity.DynamicProperty;
import com.api.jello.util.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenchen
 */
@RestController
@RequestMapping("dynamic/property")
@Slf4j
public class DynamicPropertyController {
    @Autowired
    DynamicPropertyDao dynamicPropertyDao;

    @PostMapping("saveProperty")
    public Object saveProperty(DynamicProperty dynamicProperty) {
        return ResultUtil.success(dynamicPropertyDao.insert(dynamicProperty));
    }

    @GetMapping("getPropertyByTableId")
    public Object getPropertyByTableId(String id) {
        return ResultUtil.success(dynamicPropertyDao.selectList(new QueryWrapper<DynamicProperty>().eq("TABLE_ID", id)));
    }
}
