package com.zzf.controller;

import com.zzf.annotation.IgnoreAuth;
import com.zzf.service.TraceService;
import com.zzf.util.ResultUtil;
import org.springframework.web.bind.annotation.*;
import com.zzf.service.LogUserService;

import javax.annotation.Resource;

/**
 * @author zzfn
 * @date 2021-06-06 1:27 上午
 */
@RestController
@RequestMapping("test")
public class LogUserController {
    @Resource
    TraceService traceService;

    @GetMapping("all")
    @IgnoreAuth
    public Object all(Integer num) {
        return ResultUtil.success(traceService.getAnyCount(num));
    }
}
