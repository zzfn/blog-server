package com.zzf.component;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.zzf.entity.Article;
import com.zzf.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author zzfn
 * @date 2021-06-27 1:06 上午
 */

@Component
@RabbitListener(queues = "${queue}")
@Slf4j
public class Receiver {
    @Autowired
    ArticleService articleService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @RabbitHandler
    public void receiveMessage(Article article) {
        log.info("开始刷新id-{}有关的缓存", article.getId());
        Set<String> keys = redisTemplate.keys("ARTICLE" + "*");
        if (CollectionUtils.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
        log.info("结束刷新id-{}有关的缓存", article.getId());
    }
}
