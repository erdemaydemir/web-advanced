package com.forguta.libs.web.auth0.constant;

public enum Auth0ScopeEnum {

    OPENID("openid"),
    PROFILE("profile"),
    EMAIL("email"),
    OFFLINE_ACCESS("offline_access");

    private final String value;

    Auth0ScopeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
