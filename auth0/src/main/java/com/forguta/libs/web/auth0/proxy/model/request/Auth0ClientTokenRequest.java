package com.forguta.libs.web.auth0.proxy.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.forguta.libs.web.auth0.constant.Auth0GrantTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Auth0ClientTokenRequest extends Auth0CommonRequest {

    @JsonProperty("grant_type")
    private final String grantType = Auth0GrantTypeEnum.CLIENT_CREDENTIALS.getValue();
}
