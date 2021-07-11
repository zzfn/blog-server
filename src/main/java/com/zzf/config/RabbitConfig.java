package com.zzf.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * @author zzfn
 * @date 2021-06-27 12:52 上午
 */
@Configurable
public class RabbitConfig {
    @Bean
    public Queue blogQueue(){
        return new Queue("blog");
    }
}
