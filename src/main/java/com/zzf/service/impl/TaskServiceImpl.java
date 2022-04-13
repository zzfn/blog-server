package com.zzf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzf.entity.Task;
import com.zzf.mapper.TaskMapper;
import com.zzf.service.TaskService;
import org.springframework.stereotype.Service;

/**
 * @author cc
 */
@Service
public class TaskServiceImpl  extends ServiceImpl<TaskMapper, Task> implements TaskService {
}
