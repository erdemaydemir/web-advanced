package com.forguta.libs.web.core.security.auth0.constant;

public enum Auth0GrantTypeEnum {

    IMPLICIT("implicit"),
    AUTHORIZATION_CODE("authorization_code"),
    CLIENT_CREDENTIALS("client_credentials"),
    PASSWORD("password"),
    REFRESH_TOKEN("refresh_token"),
    DEVICE_CODE("urn:;ietf:params:oauth:grant-type:device_code");

    private final String value;

    Auth0GrantTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
