package com.fabriciuss.repositcasadeacolhimento;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class RepositCasaDeAcolhimentoApplication {

    private static final Logger log = LoggerFactory.getLogger(RepositCasaDeAcolhimentoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RepositCasaDeAcolhimentoApplication.class, args);

        log.info("----->Aplicação JPA<-----");
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfoBuilder()
                        .title("DemoDs3 2021 API")
                        .description("Uma API REST para o Trabalho PP ")
                        .version("0.0.1-SNAPSHOT")
                        .license("MIT")
                        .licenseUrl("https://opensource.org/licenses/MIT")
                        .build())
                .select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .build();
    }
}