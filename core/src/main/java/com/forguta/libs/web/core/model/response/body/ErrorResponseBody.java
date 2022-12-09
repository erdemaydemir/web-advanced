package com.forguta.libs.web.core.model.response.body;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public final class ErrorResponseBody<T> {
    @Builder.Default
    private boolean success = false;
    private T content;
    private String failedMessage;
    private String requestPath;
    private String exception;
    private List<String> validations;
}
