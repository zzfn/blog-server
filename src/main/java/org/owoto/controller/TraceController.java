package org.owoto.controller;

import com.alibaba.druid.util.DruidWebUtils;
import org.owoto.entity.Trace;
import org.owoto.service.TraceService;
import org.owoto.util.HttpUtil;
import org.owoto.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * TRACE(Trace)表控制层
 *
 * @author makejava
 * @since 2021-06-25 16:22:10
 */
@RestController
@RequestMapping("trace")
public class TraceController {
    /**
     * 服务对象
     */
    @Resource
    private TraceService traceService;


    @PostMapping("non/save")
    public Object selectOne(@RequestBody Trace trace, HttpServletRequest request) {
        return ResultUtil.success(this.traceService.save(trace));
    }
    @GetMapping("non/ip")
    public Object selectOne(HttpServletRequest request) {
        return ResultUtil.success(DruidWebUtils.getRemoteAddr(request));
    }
    @GetMapping("non/ips")
    public Object selectOne() {
        return ResultUtil.success(HttpUtil.getIp());
    }
}
