package com.forguta.libs.web.common.exception;

import java.util.List;

public class InvalidSignupException extends RuntimeException {

    private List<String> validations;

    public InvalidSignupException(List<String> validations) {
        this.validations = validations;
    }

    public InvalidSignupException(String message) {
        super(message);
    }

    public InvalidSignupException(String message, List<String> validations) {
        super(message);
        this.validations = validations;
    }
}