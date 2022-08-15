package com.forguta.libs.web.core.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import static com.forguta.libs.web.core.model.constant.Constant.SWAGGER_URLS;

public abstract class AbstractEndpointSecurityAware {

    public final void define(HttpSecurity httpSecurity) throws Exception {
        definePublicEndpoints(httpSecurity);
        defineEndpoints(httpSecurity);
    }

    public abstract void defineEndpoints(HttpSecurity httpSecurity) throws Exception;

    private void definePublicEndpoints(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests()
                .antMatchers(SWAGGER_URLS).permitAll().and();
    }
}
