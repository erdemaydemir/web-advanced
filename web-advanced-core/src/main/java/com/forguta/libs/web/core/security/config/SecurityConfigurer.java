package com.forguta.libs.web.core.security.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springboot.KeycloakAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@EnableAutoConfiguration(exclude = {KeycloakAutoConfiguration.class})
public class SecurityConfigurer {

    @Bean
    @ConditionalOnExpression("${web-advanced.security.enabled:true} and '${web-advanced.security.provider}'.equals('KEYCLOAK')")
    public KeycloakAutoConfiguration keycloakAutoConfiguration() {
        return new KeycloakAutoConfiguration();
    }
}
