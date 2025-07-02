package com.project.crowdfunding.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI customOpenApi(){
    return new OpenAPI()
            .info(new Info()
                    .title("Crowdfunding Platform Api")
                    .description("This API handles crowdfunding campaigns, KYC, donations and user management.")
                    .contact(new Contact().name("Sujal Pandey").email("Sujalpandey2058@gmail.com"))
            )
            .servers(List.of(new Server().url("http://localhost:8080/").description("Local server")))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(
                    new Components()
                            .addSecuritySchemes(
                                    "bearerAuth",
                                    new SecurityScheme()
                                            .type(SecurityScheme.Type.HTTP)
                                            .bearerFormat("JWT")
                                            .scheme("bearer")
                                            .in(SecurityScheme.In.HEADER)
                                            .name("Authorization")
                            )
                    );

    }
}
