package com.zzf.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author zzfn
 * @date 2021-06-06 1:29 上午
 */
@Component
@Slf4j
public class TalkUtil {
    /**
     * 秘钥
     */
    public static String SECRET;
    /**
     * webhook地址
     */
    public static String URL;

    @Value("${WebHook.url}")
    private void setUrl(String url) {
        URL = url;
    }

    @Value("${WebHook.secret}")
    private void setSecret(String secret) {
        SECRET = secret;
    }

    private static String getSign(String secret, long timestamp) {
        try {
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.encodeBase64(signData));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object postMessage(String message) {
        long timestamp = System.currentTimeMillis();
        String sign = TalkUtil.getSign(SECRET, timestamp);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        JSONObject json = new JSONObject();
        JSONObject text = new JSONObject();
        text.put("text", message);
        json.put("msg_type", "text");
        json.put("timestamp", timestamp);
        json.put("sign", sign);
        json.put("content", text);
        HttpEntity<String> request = new HttpEntity<>(JSON.toJSONString(json), httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, request, String.class);
        return ResultUtil.success(JSON.parseObject(responseEntity.getBody()));
    }
}
