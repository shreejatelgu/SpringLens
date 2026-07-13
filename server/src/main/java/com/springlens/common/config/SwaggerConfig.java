package com.springlens.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springLensOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("SpringLens API")
                                .description("AI Copilot for Spring Boot Architecture")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .name("Shreeja Telgu")
                                                .email("shhreejatelgu57@gmail.com")
                                                .url("https://github.com/shreejatelgu/SpringLens")
                                )
                                .license(
                                        new License()
                                                .name("MIT")
                                )
                );
    }
}