package com.forguta.libs.web.auth0.proxy.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.forguta.libs.web.auth0.constant.Auth0GrantTypeEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Auth0RefreshTokenRequest extends Auth0CommonRequest {

    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("grant_type")
    private final String grantType = Auth0GrantTypeEnum.REFRESH_TOKEN.getValue();
}
