package com.ohayo.moyamoya.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private const val JWT = "JWT"

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .addSecurityItem(SecurityRequirement().addList(JWT))
        .components(
            Components().addSecuritySchemes(
                JWT, SecurityScheme()
                    .name(JWT)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat(JWT)
            )
        )
    
}