package com.forguta.libs.web.auth0.proxy.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Auth0UserInfoResponse {

    private String sub;
    private String name;
    private String nickname;
    private String profile;
    private String picture;
    private String website;
    private String email;
    private String gender;
    private String birthdate;
    private String zoneinfo;
    private String locale;
    private Address address;
    @JsonProperty("givenName")
    private String given_name;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("middle_name")
    private String middleName;
    @JsonProperty("preferred_username")
    private String preferredUsername;
    @JsonProperty("email_verified")
    private boolean emailVerified;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("phone_number_verified")
    private boolean phoneNumberVerified;
    @JsonProperty("updated_at")
    private String updatedAt;

    public static class Address {
        private String country;
    }
}
