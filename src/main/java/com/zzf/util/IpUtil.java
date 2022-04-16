package com.zzf.util;

import com.alibaba.druid.util.DruidWebUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author cc
 */
@Slf4j
public class IpUtil {
    public static String Ip2AddressKey;

    @Value("${Ip2AddressKey}")
    private void setIp2AddressKey(String ip2AddressKey) {
        Ip2AddressKey = ip2AddressKey;
    }

    public static String getIp() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        return DruidWebUtils.getRemoteAddr(request);
    }

    public static String getAddress(String ip) {
        log.info("ip:{}", ip);
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        map.put("key", Ip2AddressKey);
        map.put("ip", ip);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://restapi.amap.com/v3/ip?key={key}&ip={ip}", String.class, map);
        JSONObject body = JSON.parseObject(responseEntity.getBody());
        if (Objects.nonNull(body) && body.get("province") instanceof String && body.get("city") instanceof String) {
            return body.get("province").toString() + body.get("city").toString();
        }
        return "";
    }
}
