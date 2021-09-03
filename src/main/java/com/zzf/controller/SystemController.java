package com.zzf.controller;

import com.alibaba.fastjson.JSON;
import com.zzf.util.RedisUtil;
import com.zzf.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author zzfn
 * @date 2021-09-03 10:47 下午
 */
@RestController
@RequestMapping("system")
@Api(tags = "os信息")
public class SystemController {
    @Autowired
    RedisUtil redisUtil;

    @ApiOperation("获取服务器信息")
    @GetMapping("os")
    public Object listTags(String server) {
        String url = (String) redisUtil.get(server);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url,String.class);
        return ResultUtil.success(JSON.parseObject(responseEntity.getBody()));
    }
}
