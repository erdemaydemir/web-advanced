package com.forguta.libs.web.auth0.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.forguta.commons.util.MyObjectMapper;
import com.forguta.libs.web.common.AuthSessionInformationSupplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class Auth0AuthSessionInformationSupplier implements AuthSessionInformationSupplier {

    private static final String APP_USER_KEY = "app_user";
    private final MyObjectMapper myObjectMapper;

    private UserInfo userInfo;

    @PostConstruct
    public void init() {
        System.out.println("loaded.");
        userInfo = getInfo();
    }

    @PreDestroy
    public void destroy() {
        userInfo = null;
    }

    @Override
    public String userId() {
        return this.userInfo.getUserId();
    }

    @Override
    public String username() {
        return this.userInfo.getUsername();
    }

    @Override
    public String email() {
        return this.userInfo.getEmail();
    }

    @Override
    public List<String> permissions() {
        return this.userInfo.getPermissions();
    }

    @Override
    public List<String> roles() {
        return this.userInfo.getRoles();
    }

    private UserInfo getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            if (authentication instanceof JwtAuthenticationToken) {
                JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
                Map<String, Object> claims = jwtAuthenticationToken.getToken().getClaims();
                return myObjectMapper.convertValue(claims.get(APP_USER_KEY), UserInfo.class);
            }
        }
        return null;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        @JsonProperty("user_id")
        private String userId;
        private String username;
        private String email;
        @JsonProperty("email_verified")
        private String emailVerified;
        private List<String> permissions;
        private List<String> roles;
    }
}
