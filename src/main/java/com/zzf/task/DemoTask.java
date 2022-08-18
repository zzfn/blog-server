package com.zzf.task;

import com.zzf.util.MailUtil;
import com.zzf.util.BotUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author cc
 */
@Slf4j
@Component
public class DemoTask {

    @Resource
    MailUtil mailUtil;
    @Scheduled(cron = "0 0 2 ? * *")
    public void run() {
        log.info("定时任务执行成功");
        BotUtil.postMessage("定时任务执行成功");
    }
}
