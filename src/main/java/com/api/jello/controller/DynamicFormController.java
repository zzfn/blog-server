package com.api.jello.controller;

import com.api.jello.dao.DynamicFormDao;
import com.api.jello.dao.DynamicPropertyDao;
import com.api.jello.dao.DynamicRecordDao;
import com.api.jello.dao.DynamicTableDao;
import com.api.jello.entity.DynamicForm;
import com.api.jello.entity.DynamicProperty;
import com.api.jello.entity.DynamicTable;
import com.api.jello.util.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenchen
 */
@RestController
@RequestMapping("dynamic/form")
@Slf4j
public class DynamicFormController {
    @Autowired
    DynamicFormDao dynamicFormDao;
    @Autowired
    DynamicPropertyDao dynamicPropertyDao;
    @GetMapping("getFormByRecordId")
    public Object getFormByRecordId(String id) {
        List<DynamicForm> dynamicFormList=dynamicFormDao.selectList(new QueryWrapper<DynamicForm>().eq("RECORD_ID", id));
        for (DynamicForm dynamicForm:dynamicFormList){
          dynamicForm.setDynamicProperty(dynamicPropertyDao.selectOne(new QueryWrapper<DynamicProperty>().eq("id", dynamicForm.getPropertyId())));
        }
        return ResultUtil.success(dynamicFormList);
    }
}
