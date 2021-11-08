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
public class TraceTask {
    @Resource
    TraceService traceService;

    @Resource
    MailUtil mailUtil;
    @Scheduled(cron = "0 0 3 ? * *")
    public void run() {
        Object num=traceService.removeExpiredTrace();
        mailUtil.sendEmail("admin@zzfzzf.com","定时任务执行成功","定时任务执行成功,删除"+num+"条日志");
    }
}
