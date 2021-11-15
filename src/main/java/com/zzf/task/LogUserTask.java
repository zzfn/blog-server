package com.zzf.task;

import com.zzf.service.TraceService;
import com.zzf.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author cc
 */
@Slf4j
@Component
public class LogUserTask {
    @Resource
    TraceService traceService;

    @Resource
    MailUtil mailUtil;
    @Scheduled(cron = "0 0 2 ? * *")
    public void run() {
        traceService.getAnyCount(-1);
    }
}
