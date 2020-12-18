package org.owoto.controller;

import org.owoto.dao.DynamicFormDao;
import org.owoto.dao.DynamicPropertyDao;
import org.owoto.entity.DynamicForm;
import org.owoto.entity.DynamicProperty;
import org.owoto.util.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
