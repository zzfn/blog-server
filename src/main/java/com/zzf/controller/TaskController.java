package com.zzf.controller;

import com.zzf.annotation.IgnoreAuth;
import com.zzf.entity.Task;
import com.zzf.service.TaskService;
import com.zzf.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

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
    public Object saveTask(@RequestBody Task task) {
        return ResultUtil.success(taskService.saveOrUpdate(task));
    }
}
