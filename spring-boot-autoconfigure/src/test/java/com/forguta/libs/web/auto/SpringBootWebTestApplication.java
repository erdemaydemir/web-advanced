package com.forguta.libs.web.auto;

import com.forguta.libs.web.auth0.model.request.RefreshTokenRequest;
import com.forguta.libs.web.auth0.model.request.SignupRequest;
import com.forguta.libs.web.auth0.model.request.TokenRequest;
import com.forguta.libs.web.auth0.model.response.SignupResponse;
import com.forguta.libs.web.auth0.model.response.TokenResponse;
import com.forguta.libs.web.auth0.service.Auth0AuthSessionInformationSupplier;
import com.forguta.libs.web.auth0.service.Auth0AuthenticationAPIService;
import com.forguta.libs.web.common.AbstractEndpointSecurityAware;
import com.forguta.libs.web.common.AuthSessionInformationSupplier;
import com.forguta.libs.web.core.model.response.GenericResponseEntity;
import com.forguta.libs.web.core.model.response.body.GenericResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@ComponentScan("com.forguta")
public class SpringBootWebTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebTestApplication.class, args);
    }

    @RestController
    @RequestMapping("/api")
    @RequiredArgsConstructor
    public class Controller {

        private final Auth0AuthenticationAPIService auth0AuthenticationAPIService;
        private final AuthService authService;

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
            authService.logout();
            return GenericResponseEntity.ok("");
        }
    }

    @Service
    @RequiredArgsConstructor
    @Slf4j
    public static class AuthService {

        private final Auth0AuthenticationAPIService auth0AuthenticationAPIService;
        private final AuthSessionInformationSupplier auth0AuthSessionInformationSupplier;

        public void logout() {
            log.info(auth0AuthSessionInformationSupplier.userId());
            auth0AuthenticationAPIService.logout();
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
