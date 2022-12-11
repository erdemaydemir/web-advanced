package com.forguta.libs.web.auth0.proxy.model.response.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Auth0TokenErrorResponse {

    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
}
