package com.forguta.libs.web.auth0.proxy.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.forguta.commons.util.MyObjectMapper;
import com.forguta.libs.web.auth0.proxy.model.response.error.Auth0SignupErrorResponse;
import com.forguta.libs.web.common.exception.InvalidSignupException;
import com.forguta.libs.web.common.exception.UnknownErrorClientException;
import com.forguta.libs.web.common.feign.FeignHttpExceptionHandler;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SignupClientExceptionHandler implements FeignHttpExceptionHandler {

    private static final String INVALID_PASSWORD = "invalid_password";
    private static final String INVALID_SIGNUP = "invalid_signup";

    private final MyObjectMapper objectMapper;

    @Override
    public Exception handle(Response response) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(response.body().asInputStream());
        Auth0SignupErrorResponse auth0SignupErrorResponse = objectMapper.convertValue(jsonNode, Auth0SignupErrorResponse.class);
        if (INVALID_PASSWORD.equals(auth0SignupErrorResponse.getCode())) {
            List<Map<String, ?>> rules = objectMapper.convertValue(auth0SignupErrorResponse.getDescription().get("rules"), new TypeReference<>() {
            });
            List<String> messages = rules.stream().map(r -> r.get("message").toString()).collect(Collectors.toList());
            return new InvalidSignupException(auth0SignupErrorResponse.getName(), messages);
        }
        if (INVALID_SIGNUP.equals(auth0SignupErrorResponse.getCode())) {
            return new InvalidSignupException(auth0SignupErrorResponse.getName());
        }
        return new UnknownErrorClientException();
    }
}
