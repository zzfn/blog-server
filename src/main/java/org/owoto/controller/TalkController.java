package org.owoto.controller;

import com.alibaba.fastjson.JSON;
import org.owoto.entity.TalkBot;
import org.owoto.mapper.TalkBotMapper;
import org.owoto.util.ResultUtil;
import org.owoto.util.TalkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author zzfn
 * @date 2021-06-06 1:27 上午
 */
@RestController
@RequestMapping("talk")
public class TalkController {
    @Autowired
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
    public Object send(@RequestBody String msg, @RequestParam String id) {
        TalkBot talkBot = talkBotMapper.selectById(id);
        long timestamp = System.currentTimeMillis();
        String sign = TalkUtil.getSign(talkBot.getSecret(), timestamp);
        String url = String.format("https://oapi.dingtalk.com/robot/send?access_token=%s&timestamp=%s&sign=%s", talkBot.getAccessToken(), timestamp, sign);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(msg, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
        return ResultUtil.success(JSON.parseObject(responseEntity.getBody()));
    }
}
