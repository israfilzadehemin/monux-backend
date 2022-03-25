package com.budgetmanagementapp.configuration;

import static com.budgetmanagementapp.utility.Constant.JWT_HEADER;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springOpenApiConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Monux backend services OpenAPI documentation")
                        .description("This documentation includes all endpoints used through web and mobile services")
                        .version("1.0.0")
                        .license(new License().name("Monux 1.0 license").url("https://monux.herokuapp.com/swagger-ui/#/")))
                .addSecurityItem(new SecurityRequirement().addList(JWT_HEADER))
                .components(
                        new Components()
                                .addSecuritySchemes(JWT_HEADER, apiKeySecuritySchema())
                );
    }

    public SecurityScheme apiKeySecuritySchema() {
        return new SecurityScheme()
                .name(JWT_HEADER) // authorisation-token
                .description("Add token which you have got as a response from Login endpoint")
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.APIKEY);
    }

}
