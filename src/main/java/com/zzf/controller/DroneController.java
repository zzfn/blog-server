package com.zzf.controller;

import com.alibaba.fastjson.JSON;
import com.zzf.util.ResultUtil;
import com.zzf.vo.BuildCreateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author cc
 */
@RestController
@RequestMapping("drone")
@Slf4j
public class DroneController {
    @Value("${DRONE_BEARER}")
    private String droneBearer;

    private final static String DRONE_URL = "https://drone.zzfzzf.com";

    @GetMapping("repos")
    public Object repos() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(droneBearer);
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                DRONE_URL + "/api/user/repos?latest=true",
                HttpMethod.GET,
                request,
                String.class
        );
        return ResultUtil.success(JSON.parse(response.getBody()));
    }

    @GetMapping("builds")
    public Object builds(String owner, String repo) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(droneBearer);
        HttpEntity<String> request = new HttpEntity<>(JSON.toJSONString(new Object()), httpHeaders);
        String url = DRONE_URL + String.format("/api/repos/%s/%s/builds?page=1&per_page=10", owner, repo);
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class
        );
        return ResultUtil.success(JSON.parse(response.getBody()));
    }

    @PostMapping("buildCreate")
    @PreAuthorize("hasRole('ADMIN')")
    public Object buildCreate(@RequestBody BuildCreateVo buildCreateVo) {
        log.info(buildCreateVo.getRepo(),buildCreateVo.getOwner());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(droneBearer);
        HttpEntity<String> request = new HttpEntity<>(JSON.toJSONString(new Object()), httpHeaders);
        String url = DRONE_URL + String.format("/api/repos/%s/%s/builds?branch=%s", buildCreateVo.getOwner(), buildCreateVo.getRepo(), buildCreateVo.getBranch());
        log.info(url);
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );
        log.info(response.getBody());
        return ResultUtil.success(JSON.parse(response.getBody()));
    }
}
