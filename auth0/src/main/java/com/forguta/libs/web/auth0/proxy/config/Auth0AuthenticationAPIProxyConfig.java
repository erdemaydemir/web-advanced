package com.forguta.libs.web.auth0.proxy.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Slf4j
@ConditionalOnExpression("${web-advanced.security.auth0.ssoEnable:true}")
@Configuration
@RequiredArgsConstructor
public class Auth0AuthenticationAPIProxyConfig {

    @Bean
    public Auth0AuthenticationAPIInterceptor auth0AuthenticationAPIInterceptor() {
        return new Auth0AuthenticationAPIInterceptor();
    }

    @Slf4j
    public static class Auth0AuthenticationAPIInterceptor implements RequestInterceptor {

        @Override
        public void apply(RequestTemplate requestTemplate) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthenticationToken) {
                JwtAuthenticationToken details = (JwtAuthenticationToken) authentication;
                requestTemplate.header(HttpHeaders.AUTHORIZATION, String.format("%s %s", OAuth2AccessToken.TokenType.BEARER.getValue(), details.getToken().getTokenValue()));
            }
        }
    }
}
