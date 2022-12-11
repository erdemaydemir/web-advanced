package com.forguta.libs.web.auth0.proxy.handler;

import com.forguta.commons.util.MyObjectMapper;
import com.forguta.libs.web.auth0.proxy.model.response.error.Auth0TokenErrorResponse;
import com.forguta.libs.web.common.exception.InvalidAccessTokenException;
import com.forguta.libs.web.common.feign.FeignHttpExceptionHandler;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OauthTokenClientExceptionHandler implements FeignHttpExceptionHandler {

    private final MyObjectMapper objectMapper;

    @Override
    public Exception handle(Response response) {
        Auth0TokenErrorResponse auth0TokenErrorResponse = objectMapper.convertValue(response.body(), Auth0TokenErrorResponse.class);
        return new InvalidAccessTokenException(auth0TokenErrorResponse.getError(), auth0TokenErrorResponse.getErrorDescription());
    }
}
