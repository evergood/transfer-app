package com.bank.transfer.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {

    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI().info(Info().title("Transfer").version("1.0.0-SNAPSHOT").description("Money transfer"))
    }
}