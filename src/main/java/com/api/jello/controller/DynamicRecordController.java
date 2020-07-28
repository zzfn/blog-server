package com.api.jello.controller;

import com.api.jello.dao.DynamicRecordDao;
import com.api.jello.dao.DynamicTableDao;
import com.api.jello.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenchen
 */
@RestController
@RequestMapping("dynamic/record")
@Slf4j
public class DynamicRecordController {
    @Autowired
    DynamicRecordDao dynamicRecordDao;

    @GetMapping("getAllTable")
    public Object getAllTable() {
        return ResultUtil.success(dynamicRecordDao.selectList(null));
    }
}
