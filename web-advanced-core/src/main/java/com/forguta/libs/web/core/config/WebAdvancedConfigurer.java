package com.forguta.libs.web.core.config;

import com.forguta.libs.web.core.error.CustomHandlerExceptionResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@ComponentScan(basePackages = "com.forguta.libs.web")
@Configuration
public class WebAdvancedConfigurer {

    @Bean
    public CustomHandlerExceptionResolver customHandlerExceptionResolver() {
        return new CustomHandlerExceptionResolver();
    }
}
