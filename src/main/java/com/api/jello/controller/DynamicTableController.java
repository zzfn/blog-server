package com.api.jello.controller;

import com.api.jello.dao.DynamicTableDao;
import com.api.jello.entity.DynamicTable;
import com.api.jello.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;

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
    public Object getAllTable() {
        return ResultUtil.success(dynamicTableDao.selectList(null));
    }

    @PostMapping("saveTable")
    public Object saveTable(DynamicTable dynamicTable) {
        return ResultUtil.success(dynamicTableDao.insert(dynamicTable));
    }
}
