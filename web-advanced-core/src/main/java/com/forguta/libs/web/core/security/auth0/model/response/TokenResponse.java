package com.forguta.libs.web.core.security.auth0.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TokenResponse {

    private String scope;
    private String accessToken;
    private String idToken;
    private String refreshToken;
    private long expiresIn;
    private String tokenType;
}
