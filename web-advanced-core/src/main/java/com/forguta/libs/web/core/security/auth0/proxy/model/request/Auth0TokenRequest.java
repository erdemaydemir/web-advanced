package com.forguta.libs.web.core.security.auth0.proxy.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.forguta.libs.web.core.security.auth0.constant.Auth0GrantTypeEnum;
import com.forguta.libs.web.core.security.auth0.constant.Auth0ScopeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Auth0TokenRequest extends Auth0CommonRequest {

    private String username;
    private String password;
    private final String scope = Auth0ScopeEnum.OFFLINE_ACCESS.getValue();
    @JsonProperty("grant_type")
    private final String grantType = Auth0GrantTypeEnum.PASSWORD.getValue();
}
