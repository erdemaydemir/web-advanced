package com.forguta.libs.web.auth0.proxy.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forguta.libs.web.auth0.proxy.model.response.Auth0ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomErrorDecoder implements ErrorDecoder {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        Auth0ErrorResponse auth0ErrorResponse = getAuth0ErrorResponse(response);
        String errorDescription = auth0ErrorResponse.getErrorDescription();
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        if (responseStatus.is5xxServerError()) {
            return HttpServerErrorException.create(auth0ErrorResponse.getErrorDescription(), responseStatus, errorDescription, new HttpHeaders(), null, null);
        } else if (responseStatus.is4xxClientError()) {
            return HttpClientErrorException.create(auth0ErrorResponse.getErrorDescription(), responseStatus, errorDescription, new HttpHeaders(), null, null);
        } else {
            return errorDecoder.decode(methodKey, response);
        }
    }

    private Auth0ErrorResponse getAuth0ErrorResponse(Response response) {
        Auth0ErrorResponse auth0ErrorResponse;
        try (InputStream responseBodyArr = response.body().asInputStream()) {
            auth0ErrorResponse = OBJECT_MAPPER.readValue(responseBodyArr, Auth0ErrorResponse.class);
            if (StringUtils.hasText(auth0ErrorResponse.getCode())) {
                auth0ErrorResponse.setError(auth0ErrorResponse.getCode());
                auth0ErrorResponse.setErrorDescription(auth0ErrorResponse.getMessage());
            }
        } catch (IOException e) {
            return Auth0ErrorResponse.builder().error(e.getClass().getSimpleName()).errorDescription(e.getMessage()).build();
        }
        return auth0ErrorResponse;
    }
}