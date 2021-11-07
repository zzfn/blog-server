package com.zzf.task;

import com.zzf.service.TraceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cc
 */
@Slf4j
@Component
public class TraceTask {
    @Resource
    TraceService traceService;
    @Scheduled(cron = "0 0 3 ? * *")
    public void run() {
        traceService.removeExpiredTrace();
    }
}
