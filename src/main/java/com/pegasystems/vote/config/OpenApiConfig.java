package com.pegasystems.vote.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Milad Ranjbari
 * @version 2022.6.1
 * @since 6/01/22
 * Change Swagger Header UI
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Eurovision song contest voting API")
                        .description("Eurovision song contest voting API V1 Definition ")
                        .version("v1")
                        .contact(new Contact()
                                .name("Milad Ranjbari")
                                .url("https://www.linkedin.com/in/milad-ranjbari/")
                                .email("miladranjbari@gmail.com"))
                );
    }
}
