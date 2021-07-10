package com.zzf.controller;

import com.zzf.entity.LoginLog;
import com.zzf.service.LoginLogService;
import com.zzf.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * LOGIN_LOG(LoginLog)表控制层
 *
 * @author makejava
 * @since 2021-06-26 16:28:25
 */
@RestController
@RequestMapping("loginLog")
public class LoginLogController {
    /**
     * 服务对象
     */
    @Resource
    private LoginLogService loginLogService;


    @GetMapping("save")
    public Object save(@RequestBody LoginLog loginLog) {
        return ResultUtil.success(this.loginLogService.save(loginLog));
    }

}
