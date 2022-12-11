package com.forguta.libs.web.auto;

import com.forguta.libs.web.auth0.model.request.RefreshTokenRequest;
import com.forguta.libs.web.auth0.model.request.SignupRequest;
import com.forguta.libs.web.auth0.model.request.TokenRequest;
import com.forguta.libs.web.auth0.model.response.SignupResponse;
import com.forguta.libs.web.auth0.model.response.TokenResponse;
import com.forguta.libs.web.auth0.service.Auth0AuthenticationAPIService;
import com.forguta.libs.web.common.AbstractEndpointSecurityAware;
import com.forguta.libs.web.core.model.response.GenericResponseEntity;
import com.forguta.libs.web.core.model.response.body.GenericResponseBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ComponentScan("com.forguta")
public class SpringBootWebTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebTestApplication.class, args);
    }

    @Tag(name = "Authentication", description = "Authentication")
    @RequiredArgsConstructor
    @RestController
    @RequestMapping("/api/auth")
    public class AuthController {

        private final Auth0AuthenticationAPIService auth0AuthenticationAPIService;

        @PostMapping("/signup")
        public ResponseEntity<GenericResponseBody<SignupResponse>> signup(@RequestBody SignupRequest signupRequest) {
            SignupResponse signup = auth0AuthenticationAPIService.signup(signupRequest);
            return GenericResponseEntity.ok(signup);
        }

        @PostMapping("/authorize")
        public ResponseEntity<GenericResponseBody<TokenResponse>> authorize(@RequestBody TokenRequest tokenRequest) {
            TokenResponse tokenResponse = auth0AuthenticationAPIService.authorize(tokenRequest);
            return GenericResponseEntity.ok(tokenResponse);
        }

        @PostMapping("/refresh")
        public ResponseEntity<GenericResponseBody<TokenResponse>> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
            TokenResponse tokenResponse = auth0AuthenticationAPIService.refresh(refreshTokenRequest);
            return GenericResponseEntity.ok(tokenResponse);
        }

        @PostMapping("/logout")
        public ResponseEntity<GenericResponseBody<Object>> logout() {
            auth0AuthenticationAPIService.logout();
            return GenericResponseEntity.ok("");
        }
    }

    @Component
    public static class EndpointSecurityAware extends AbstractEndpointSecurityAware {

        @Override
        public void defineEndpoints(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.authorizeHttpRequests()
                    .mvcMatchers("/api/**").permitAll()
                    .anyRequest().permitAll().and();
        }

    }
}
