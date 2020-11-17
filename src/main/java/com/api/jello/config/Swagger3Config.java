package com.api.jello.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author cc
 */
@Configuration
public class Swagger3Config {
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("swagger3")
                .description("api文档")
                .contact(new Contact("", "", ""))
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }


    @Bean
    public Docket controllerApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.api.jello"))
                .paths(PathSelectors.any())
                .build();
    }
}
