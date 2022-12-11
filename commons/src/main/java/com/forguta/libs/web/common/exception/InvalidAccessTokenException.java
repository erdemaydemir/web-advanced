package com.forguta.libs.web.common.exception;

public class InvalidAccessTokenException extends RuntimeException {

    private final String error;
    private final String errorDescription;

    public InvalidAccessTokenException(String error, String errorDescription) {
        super(errorDescription);
        this.error = error;
        this.errorDescription = errorDescription;
    }
}
