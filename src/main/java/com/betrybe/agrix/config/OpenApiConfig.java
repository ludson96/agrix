package com.betrybe.agrix.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger / OpenAPI 3 para a API Agrix.
 */
@Configuration
public class OpenApiConfig {

  /**
   * Configura os metadados da API e o esquema de segurança Bearer JWT.
   */
  @Bean
  public OpenAPI customOpenApi() {
    final String securitySchemeName = "BearerAuth";

    return new OpenAPI()
        .info(new Info()
            .title("Agrix API - Sistema de Gestão Agrícola")
            .version("1.0.0")
            .description("API RESTful desenvolvida com Spring Boot 3 para gestão "
                + "de fazendas, plantações, fertilizantes e autenticação JWT.")
            .contact(new Contact()
                .name("Portfólio Backend")
                .url("https://github.com"))
            .license(new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT")))
        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
        .components(new Components()
            .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                .name(securitySchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Insira o token JWT gerado no endpoint /auth/login")));
  }
}
