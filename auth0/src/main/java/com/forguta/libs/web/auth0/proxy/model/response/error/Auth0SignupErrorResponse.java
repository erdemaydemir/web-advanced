package com.forguta.libs.web.auth0.proxy.model.response.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Auth0SignupErrorResponse {

    private String code;
    private String name;
    private String message;
    private Map<String, ?> description;
    private String policy;
    private String statusCode;
    private String repeatable;
}
