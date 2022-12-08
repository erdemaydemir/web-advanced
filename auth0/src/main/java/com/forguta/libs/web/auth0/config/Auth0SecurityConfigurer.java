package com.forguta.libs.web.auth0.config;

import com.forguta.libs.web.auth0.config.properties.Auth0Properties;
import com.forguta.libs.web.auth0.proxy.Auth0AuthenticationAPIProxy;
import com.forguta.libs.web.common.AbstractEndpointSecurityAware;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@ComponentScan(basePackages = "com.forguta.libs.web.auth0")
@EnableFeignClients(clients = Auth0AuthenticationAPIProxy.class)
@ConditionalOnExpression("${web-advanced.security.enabled:true} and '${web-advanced.security.provider}'.equals('AUTH0')")
public class Auth0SecurityConfigurer {

    private final Auth0Properties auth0Properties;
    private final AbstractEndpointSecurityAware abstractEndpointSecurityAware;

    @Bean
    @ConditionalOnExpression("${web-advanced.security.enabled}")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors();
        abstractEndpointSecurityAware.define(http);
        http.oauth2ResourceServer().jwt();
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(auth0Properties.getIssuer());
        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(auth0Properties.getAuthenticationAPI().getApiAudience());
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(auth0Properties.getIssuer());
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);
        jwtDecoder.setJwtValidator(withAudience);
        return jwtDecoder;
    }

    static class AudienceValidator implements OAuth2TokenValidator<Jwt> {

        private final String audience;

        AudienceValidator(String audience) {
            this.audience = audience;
        }

        public OAuth2TokenValidatorResult validate(Jwt jwt) {
            OAuth2Error error = new OAuth2Error("invalid_token", "The required audience is missing", null);
            if (jwt.getAudience().contains(audience)) {
                return OAuth2TokenValidatorResult.success();
            }
            return OAuth2TokenValidatorResult.failure(error);
        }
    }
}