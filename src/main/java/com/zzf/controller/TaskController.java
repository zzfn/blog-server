package com.zzf.controller;

import com.zzf.annotation.IgnoreAuth;
import com.zzf.entity.Task;
import com.zzf.mapper.TalkBotMapper;
import com.zzf.mapper.TaskMapper;
import com.zzf.service.TaskService;
import com.zzf.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author cc
 */
@RestController
@RequestMapping("task")
public class TaskController {
    @Resource
    TaskService taskService;

    @GetMapping("list")
    @IgnoreAuth
    public Object getAllList() {
        return ResultUtil.success(taskService.list(null));
    }

    @PostMapping("save")
    @IgnoreAuth
    public Object saveTask(Task task) {
        return ResultUtil.success(taskService.saveOrUpdate(task));
    }
}
