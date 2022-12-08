package com.forguta.libs.web.auth0.proxy.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Auth0ErrorResponse {

    private String code;
    private String name;
    private String message;
    private Map<String, ?> description;
    private String policy;
    private String statusCode;
    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
}
