package org.owoto;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author cach1e
 */
@SpringBootApplication
@MapperScan("org.owoto.mapper")
@EnableAsync
@EnableScheduling
@EnableCaching
@EnableOpenApi
public class JelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(JelloApplication.class, args);
    }
}
