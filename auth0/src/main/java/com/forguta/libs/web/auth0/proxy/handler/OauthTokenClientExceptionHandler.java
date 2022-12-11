package com.forguta.libs.web.auth0.proxy.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.forguta.commons.util.MyObjectMapper;
import com.forguta.libs.web.auth0.proxy.model.response.error.Auth0TokenErrorResponse;
import com.forguta.libs.web.common.exception.InvalidAccessTokenException;
import com.forguta.libs.web.common.feign.FeignHttpExceptionHandler;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OauthTokenClientExceptionHandler implements FeignHttpExceptionHandler {

    private final MyObjectMapper objectMapper;

    @Override
    public Exception handle(Response response) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(response.body().asInputStream());
        Auth0TokenErrorResponse auth0TokenErrorResponse = objectMapper.convertValue(jsonNode, Auth0TokenErrorResponse.class);
        return new InvalidAccessTokenException(auth0TokenErrorResponse.getError(), auth0TokenErrorResponse.getErrorDescription());
    }
}
