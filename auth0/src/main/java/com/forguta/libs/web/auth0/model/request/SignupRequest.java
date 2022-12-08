package com.forguta.libs.web.auth0.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignupRequest {

    private String email;
    private String password;
    private String username;
    private String name;
    private String nickname;
    private String picture;
    private String givenName;
    private String familyName;
    private Map<String, String> userMetadata;
}
