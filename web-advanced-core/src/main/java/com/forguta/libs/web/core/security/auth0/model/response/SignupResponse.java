package com.forguta.libs.web.core.security.auth0.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignupResponse {

    private String _id;
    private String email;
    private String name;
    private String username;
    private String nickname;
    private String picture;
    private boolean emailVerified;
    private String givenName;
    private String familyName;
}
