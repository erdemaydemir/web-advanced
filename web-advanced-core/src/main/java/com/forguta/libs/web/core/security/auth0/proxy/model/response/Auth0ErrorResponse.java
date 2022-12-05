package com.forguta.libs.web.core.security.auth0.proxy.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Auth0ErrorResponse {

    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
}
