package com.example.enotes.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI();

        Info info = new Info();
        info.setTitle("Enotes API's");
        info.setDescription("Enotes API's Documentation. Feel free to try on your own");
        info.setVersion("1.0.0");
        info.setTermsOfService("https://enotes.com/terms-of-service");
        info.setContact(new Contact().email("auriohendrianoko@gmail.com")
                .name("Aurio Rajaa").url("https://portfolio-aurio-frontend.vercel.app/"));
        info.setLicense(new License().name("Enotes 1.0").url("https://enotes.com"));

        List<Server> serverList = List.of(
                new Server().description("Dev").url("http://localhost:8080"),
                new Server().description("Test").url("http://localhost:8081"),
                new Server().description("Prod").url("http://localhost:8082"));

        // Bearer
        SecurityScheme securityScheme = new SecurityScheme().name("Authorization").scheme("bearer")
                        .bearerFormat("JWT").in(SecurityScheme.In.HEADER);

        Components components = new Components().addSecuritySchemes("Token", securityScheme);

        openAPI.setServers(serverList);
        openAPI.setInfo(info);
        openAPI.setComponents(components);
        openAPI.setSecurity(List.of(new SecurityRequirement().addList("Token")));

        return openAPI;
    }
}
