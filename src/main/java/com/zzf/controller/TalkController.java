package com.zzf.controller;

import com.alibaba.fastjson.JSON;
import com.zzf.annotation.IgnoreAuth;
import com.zzf.entity.TalkBot;
import com.zzf.mapper.TalkBotMapper;
import com.zzf.util.ResultUtil;
import com.zzf.util.TalkUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author zzfn
 * @date 2021-06-06 1:27 上午
 */
@RestController
@RequestMapping("talk")
public class TalkController {
    @Resource
    TalkBotMapper talkBotMapper;

    @GetMapping("all")
    @PreAuthorize("hasRole('ADMIN')")
    public Object all() {
        return ResultUtil.success(talkBotMapper.selectList(null));
    }

    @PostMapping("save")
    @PreAuthorize("hasRole('ADMIN')")
    public Object add(@RequestBody TalkBot talkBot) {
        return ResultUtil.success(talkBotMapper.insert(talkBot));
    }

    @PostMapping("send")
    @PreAuthorize("hasRole('ADMIN')")
    public Object send(@RequestBody TalkBot talkBot) {
        return ResultUtil.success(TalkUtil.postMessage(talkBot.getName()));
    }
}
