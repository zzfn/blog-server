package com.zzf.controller;

import com.alibaba.fastjson.JSON;
import com.zzf.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author cc
 */
@RestController
@RequestMapping("drone")
@Slf4j
public class DroneController {
    @Value("${DRONE_BEARER}")
    private String DRONE_BEARER;

    private final static String DRONE_URL = "https://drone.zzfzzf.com";

    @GetMapping("repos")
    public Object repos() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(DRONE_BEARER);
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                DRONE_URL+"/api/user/repos",
                HttpMethod.GET,
                request,
                String.class
        );
        return ResultUtil.success(JSON.parse(response.getBody()));
    }
    @GetMapping("builds")
    public Object builds(String owner,String repo) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(DRONE_BEARER);
        HttpEntity<String> request = new HttpEntity<>(JSON.toJSONString(new Object()), httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                DRONE_URL+String.format("/api/repos/%s/%s/builds", owner, repo),
                HttpMethod.GET,
                request,
                String.class
        );
        return ResultUtil.success(JSON.parse(response.getBody()));
    }
}
