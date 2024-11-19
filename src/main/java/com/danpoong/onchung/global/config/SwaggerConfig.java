package com.danpoong.onchung.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Value("${product.app-url}")
    private String prodUrl;

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Youth Map API Document");

        String authName = "Json Web Token";

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(authName);
        Components components = new Components()
                .addSecuritySchemes(
                        authName,
                        new SecurityScheme()
                                .name(authName)
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("Bearer")
                                .description("Access Token 입력. (Bearer X)")
                );

        Server prodServer = new Server();
        prodServer.description("Prod Server")
                .url(prodUrl);

        Server localServer = new Server();
        localServer.description("Local Server")
                .url("http://localhost:8080");

        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components)
                .info(info)
                .servers(Arrays.asList(prodServer, localServer));
    }
}
