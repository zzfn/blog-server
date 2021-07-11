package com.zzf.component;

import com.zzf.entity.Article;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zzfn
 * @date 2021-06-27 1:06 上午
 */
@Component
public class Send {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void post(Article article) {
        this.rabbitTemplate.convertAndSend("blog",article);
    }
}
