package com.zzf.component;

import com.zzf.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zzfn
 * @date 2021-06-27 1:06 上午
 */
@Component
@Slf4j
public class Send {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${queue}")
    private String queue;

    public void post(Article article) {
        log.info(queue);
        this.rabbitTemplate.convertAndSend(queue, article);
    }
}
