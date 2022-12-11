package com.forguta.libs.web.auth0.config;

import com.forguta.commons.util.MyObjectMapper;
import com.forguta.libs.web.auth0.service.Auth0AuthSessionInformationSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.context.annotation.RequestScope;

@RequiredArgsConstructor
@Configuration
@ComponentScan(basePackages = "com.forguta")
public class Auth0Configurer {

    private final MyObjectMapper objectMapper;

    @Bean
    @Lazy
    @RequestScope
    public Auth0AuthSessionInformationSupplier auth0AuthSessionInformationSupplier() {
        return new Auth0AuthSessionInformationSupplier(objectMapper);
    }
}