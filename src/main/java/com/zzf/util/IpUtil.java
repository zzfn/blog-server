package com.zzf.util;

import com.alibaba.druid.util.DruidWebUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
@Component
public class IpUtil {
    /**
     * 秘钥
     */
    public static String ADDRESS_KEY;

    @Value("${AddressKeyId}")
    private void setUrl(String addressKeyId) {
        ADDRESS_KEY = addressKeyId;
    }

    public static String getIp() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        return DruidWebUtils.getRemoteAddr(request);
    }

    public static String getAddress(String ip) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        map.put("key", ADDRESS_KEY);
        map.put("ip", ip);
        String response = restTemplate.getForObject("https://restapi.amap.com/v3/ip?key={key}&ip={ip}", String.class, map);
        JSONObject body = JSON.parseObject(response);
        if (Objects.nonNull(body) && body.get("province") instanceof String && body.get("city") instanceof String) {
            return body.get("province").toString() + body.get("city").toString();
        }
        return null;
    }
}
