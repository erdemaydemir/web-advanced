package com.forguta.libs.web.core.security.auth0.proxy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forguta.libs.web.core.security.auth0.proxy.model.response.Auth0ErrorResponse;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.io.InputStream;

@ConditionalOnExpression("${web-advanced.security.auth0.ssoEnable:true}")
@Configuration
@RequiredArgsConstructor
public class Auth0AuthenticationAPIProxyConfig {

    private static final Logger logger = LoggerFactory.getLogger(Auth0AuthenticationAPIProxyConfig.class);

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    @Bean
    public RequestInterceptor auth0AuthenticationAPIProxyInterceptor() {
        return requestTemplate -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthenticationToken) {
                JwtAuthenticationToken details = (JwtAuthenticationToken) authentication;
                requestTemplate.header(HttpHeaders.AUTHORIZATION, String.format("%s %s", OAuth2AccessToken.TokenType.BEARER.getValue(), details.getToken().getTokenValue()));
            }
        };
    }

    public static class CustomErrorDecoder implements ErrorDecoder {
        private final ErrorDecoder errorDecoder = new Default();

        @Override
        public Exception decode(String methodKey, Response response) {
            Auth0ErrorResponse auth0ErrorResponse = null;
            HttpStatus responseStatus = HttpStatus.valueOf(response.status());
            if (responseStatus.is5xxServerError()) {
                auth0ErrorResponse = getAuth0ErrorResponse(response);
                String errorDescription = auth0ErrorResponse.getErrorDescription();
                return new HttpServerErrorException(responseStatus, errorDescription != null ? errorDescription : "Internal server error.");
            } else if (responseStatus.is4xxClientError()) {
                auth0ErrorResponse = getAuth0ErrorResponse(response);
                String errorDescription = auth0ErrorResponse.getErrorDescription();
                return new HttpClientErrorException(responseStatus, errorDescription != null ? errorDescription : "Client error.");
            } else {
                return errorDecoder.decode(methodKey, response);
            }
        }

        private static Auth0ErrorResponse getAuth0ErrorResponse(Response response) {
            Auth0ErrorResponse auth0ErrorResponse;
            try (InputStream bodyIs = response.body().asInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                auth0ErrorResponse = mapper.readValue(bodyIs, Auth0ErrorResponse.class);
            } catch (IOException e) {
                return Auth0ErrorResponse.builder().error(e.getClass().getSimpleName()).errorDescription(e.getMessage()).build();
            }
            return auth0ErrorResponse;
        }
    }
}
