package com.forguta.libs.web.core.model.response;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class PageResponseEntity {

    public static <T> ResponseEntity<Page<T>> paged(Page<T> pageResponse) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(pageResponse);
    }
}
