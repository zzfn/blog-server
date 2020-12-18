package org.owoto.controller;

import org.owoto.dao.DynamicRecordDao;
import org.owoto.entity.DynamicRecord;
import org.owoto.util.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("getRecordByTableId")
    public Object getRecordByTableId(String id) {
        return ResultUtil.success(dynamicRecordDao.selectList(new QueryWrapper<DynamicRecord>().eq("TABLE_ID", id)));
    }
}
