package org.owoto.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zzfn
 * @date 2021-06-27 1:06 上午
 */
@Component
@RabbitListener(queues = "hello")
@Slf4j
public class Receiver {
    @RabbitHandler
    public void receiveMessage(String hello) {
        log.info(hello);
    }
}
