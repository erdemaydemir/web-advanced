package com.forguta.libs.web.core.config.swagger;

import com.forguta.commons.context.EnvironmentContext;
import com.forguta.libs.web.core.constant.Constant;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfigurer {

    private final String applicationName = EnvironmentContext.getApplicationName();
    private final String securitySchemeName = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openApi = new OpenAPI();
        openApi.info(new Info().title(applicationName).description(
                        "Documenting " + applicationName + "  REST API with SpringDoc and OpenAPI 3 spec")
                .version("1.0.0"));
        if (Boolean.parseBoolean(EnvironmentContext.getProperty(Constant.SECURITY_ENABLED))) {
            openApi.addSecurityItem(new SecurityRequirement()
                            .addList(securitySchemeName))
                    .components(new Components()
                            .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                    .name(securitySchemeName)
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")));
        }
        Server server = new Server();
        server.setUrl("/");
        openApi.servers(List.of(server));
        return openApi;
    }

    @Bean
    public GroupedOpenApi customApi() {
        return GroupedOpenApi.builder().group("api").pathsToMatch("/api/**").build();
    }
}
