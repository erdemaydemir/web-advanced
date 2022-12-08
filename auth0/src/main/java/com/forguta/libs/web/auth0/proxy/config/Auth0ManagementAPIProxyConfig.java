package com.forguta.libs.web.auth0.proxy.config;

import com.forguta.libs.web.auth0.model.response.TokenResponse;
import com.forguta.libs.web.auth0.service.Auth0AuthenticationAPIService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidators;

@Slf4j
@ConditionalOnExpression("${web-advanced.security.auth0.ssoEnable:true}")
@Configuration
@RequiredArgsConstructor
public class Auth0ManagementAPIProxyConfig {

    private static final OAuth2TokenValidator<Jwt> JWT_VALIDATOR = JwtValidators.createDefault();
    private final Auth0AuthenticationAPIService auth0AuthenticationAPIService;


    @Bean
    public Auth0ManagementAPIProxyConfig.Auth0ManagementAPIInterceptor auth0ManagementAPIInterceptor() {
        return new Auth0ManagementAPIProxyConfig.Auth0ManagementAPIInterceptor(auth0AuthenticationAPIService);
    }

    @Slf4j
    @RequiredArgsConstructor
    public static class Auth0ManagementAPIInterceptor implements RequestInterceptor {
        private final Auth0AuthenticationAPIService auth0AuthenticationAPIService;
        private String auth0ClientBearerToken;

        @Override
        public void apply(RequestTemplate requestTemplate) {
            if (StringUtils.isEmpty(auth0ClientBearerToken) || JWT_VALIDATOR.validate(Jwt.withTokenValue(auth0ClientBearerToken).build()).hasErrors()) {
                TokenResponse tokenResponse = auth0AuthenticationAPIService.clientAuthorize();
                auth0ClientBearerToken = tokenResponse.getAccessToken();
            }
            requestTemplate.header(HttpHeaders.AUTHORIZATION, String.format("%s %s", OAuth2AccessToken.TokenType.BEARER.getValue(), auth0ClientBearerToken));
        }
    }
}
