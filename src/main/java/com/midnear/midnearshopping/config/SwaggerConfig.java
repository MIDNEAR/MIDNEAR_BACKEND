package com.midnear.midnearshopping.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

// Swagger 접속 주소
// http://localhost:8080/swagger-ui/index.html#/
// https://metalog.store/swagger-ui/index.html

@Configuration
public class SwaggerConfig {

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl(contextPath);
        localServer.setDescription("Local Server");

        //Server prodServer = new Server();
        //prodServer.setUrl("개발서버주소");
        //prodServer.setDescription("Production Server");

        return new OpenAPI()
                .addServersItem(localServer)
                //.addServersItem(prodServer)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "bearerAuth",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")))
                .info(new Info().title("미드니어 API 명세서").version("1.0").description("Midnear Swagger"));
    }

    @Bean
    public GroupedOpenApi customGroupedOpenApi() {
        return GroupedOpenApi.builder().group("api").pathsToMatch("/**").build();
    }
}
