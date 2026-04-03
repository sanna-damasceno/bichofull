package com.bichofull.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bichoFullOpenAPI() {
        // Nome do esquema de segurança (pode ser qualquer nome)
        String securitySchemeName = "Bearer Auth";

        return new OpenAPI()
            .info(new Info()
                .title("BichoFull - API de Apostas")
                .description("Documentação oficial do sistema educacional de simulação do Jogo do Bicho.")
                .version("v1.0.0"))
            // Adiciona o requisito de segurança globalmente
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(new Components()
                .addSecuritySchemes(securitySchemeName,
                    new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }
}