package com.forguta.libs.web.common.exception;

public class UnknownErrorClientException extends RuntimeException {

    public UnknownErrorClientException() {
    }

    public UnknownErrorClientException(String message) {
        super(message);
    }
}
