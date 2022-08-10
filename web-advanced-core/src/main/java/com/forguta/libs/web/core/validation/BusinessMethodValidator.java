package com.forguta.libs.web.core.validation;

import java.util.ArrayList;
import java.util.List;

public abstract class BusinessMethodValidator {

    private final List<String> notValidMessages = new ArrayList<>();

    public boolean isValid(Object[] requestObject) {
        validate(requestObject);
        return notValidMessages.isEmpty();
    }

    protected abstract void validate(Object[] requestObject);

    public void addNotValidMessage(String message) {
        notValidMessages.add(message);
    }

    public List<String> getNotValidMessages() {
        return notValidMessages;
    }
}
