package com.zzf.controller;

import com.zzf.mapper.TalkBotMapper;
import com.zzf.mapper.TaskMapper;
import com.zzf.util.ResultUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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
    TaskMapper taskMapper;

    @GetMapping("list")
    public Object getAllList() {
        return ResultUtil.success(taskMapper.selectList(null));
    }
}
