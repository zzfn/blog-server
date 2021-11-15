package com.zzf.service.impl;

import com.zzf.entity.TalkBot;
import com.zzf.service.TalkService;
import com.zzf.util.TalkUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author chenchen
 */
@Service
public class TalkServiceImpl implements TalkService {
    @Override
    public void sendMsg(String msg) {
        System.out.println("sendMsg:" + msg);
    }
}
