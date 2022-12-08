package com.forguta.libs.web.common;

import com.forguta.libs.web.common.constant.Constant;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public abstract class AbstractEndpointSecurityAware {

    public final void define(HttpSecurity httpSecurity) throws Exception {
        definePublicEndpoints(httpSecurity);
        defineEndpoints(httpSecurity);
    }

    public abstract void defineEndpoints(HttpSecurity httpSecurity) throws Exception;

    private void definePublicEndpoints(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests()
                .antMatchers(Constant.SWAGGER_URLS).permitAll().and();
    }
}
