package com.forguta.libs.web.core.model.response;

import com.forguta.libs.web.core.model.response.body.GenericResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GenericResponseEntity {

    public static <T> ResponseEntity<GenericResponseBody<T>> ok(T body) {
        GenericResponseBody<T> genericResponseBody = GenericResponseBody.<T>builder()
                .content(body)
                .build();
        return ResponseEntity.ok(genericResponseBody);
    }

    public static <T> ResponseEntity<GenericResponseBody<T>> custom(HttpStatus status, T body) {
        GenericResponseBody<T> genericResponseBody = GenericResponseBody.<T>builder()
                .content(body)
                .build();
        return ResponseEntity.status(status).body(genericResponseBody);
    }

}
