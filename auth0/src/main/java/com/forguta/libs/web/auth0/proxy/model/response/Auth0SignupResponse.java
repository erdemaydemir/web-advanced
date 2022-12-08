package com.forguta.libs.web.auth0.proxy.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Auth0SignupResponse {

    private String _id;
    private String email;
    private String name;
    private String username;
    private String nickname;
    private String picture;
    @JsonProperty("email_verified")
    private boolean emailVerified;
    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("family_name")
    private String familyName;
}
