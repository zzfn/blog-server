//package com.api.jello.component;
//
//import com.api.jello.service.SysConfigService;
//import com.api.jello.util.RedisUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class InitRedis implements ApplicationRunner {
//    @Autowired
//    private SysConfigService sysConfigService;
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @Override
//    public void run(ApplicationArguments args)throws Exception{
//        String endpoint=sysConfigService.selectOne("endpoint").getValue();
//        String accessKeyId=sysConfigService.selectOne("accessKeyId").getValue();
//        String accessKeySecret=sysConfigService.selectOne("accessKeySecret").getValue();
//        String bucketName=sysConfigService.selectOne("bucketName").getValue();
//        redisUtil.set("endpoint",endpoint);
//        redisUtil.set("accessKeyId",accessKeyId);
//        redisUtil.set("accessKeySecret",accessKeySecret);
//        redisUtil.set("bucketName",bucketName);
//        log.info("oss初始化完成");
//    }
//}
