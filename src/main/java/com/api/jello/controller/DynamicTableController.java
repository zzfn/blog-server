package com.api.jello.controller;

import com.api.jello.dao.DynamicTableDao;
import com.api.jello.entity.DynamicTable;
import com.api.jello.util.ResultUtil;
import com.api.jello.vo.RequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

/**
 * @author chenchen
 */
@RestController
@RequestMapping("dynamic/table")
@Slf4j
public class DynamicTableController {
    @Autowired
    DynamicTableDao dynamicTableDao;

    @GetMapping("getAllTable")
    @PermitAll
    public Object getAllTable() {
        return ResultUtil.success(dynamicTableDao.selectList(null));
    }

    @PostMapping("saveTable")
    public Object saveTable(@RequestBody DynamicTable dynamicTable) {
        return ResultUtil.success(dynamicTableDao.insert(dynamicTable));
    }

    @DeleteMapping("deleteTableById")
    public Object deleteTableById(@RequestBody RequestVO requestVO) {
        return ResultUtil.success(dynamicTableDao.deleteById(requestVO.getId()));
    }
}
