package com.zzf.controller;

import com.alibaba.fastjson.JSON;
import com.zzf.component.IgnoreAuth;
import com.zzf.entity.Trace;
import com.zzf.util.HttpUtil;
import com.zzf.util.ResultUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * TRACE(Trace)表控制层
 *
 * @author makejava
 * @since 2021-06-25 16:22:10
 */
@RestController
@Slf4j
@RequestMapping("trace")
public class TraceController {
    /**
     * 服务对象
     */
    @Resource
    private MongoTemplate mongoTemplate;

    @ApiOperation("ip查询")
    @GetMapping("ip")
    @IgnoreAuth
    public Object selectOne(HttpServletRequest request) {
        return ResultUtil.success(HttpUtil.getIp());
    }

    @GetMapping("list")
    @IgnoreAuth
    public Object send() {
        Query query=new Query();
        query.with(Sort.by("time").descending());
        return ResultUtil.success(mongoTemplate.find(query,Trace.class, "logs"));
    }

    @ApiOperation("服务器信息")
    @GetMapping("server/info")
    public Object sys() {
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.getForObject("http://47.100.47.169/json/stats.json", String.class);
        return ResultUtil.success(JSON.parse(res));
    }
}
