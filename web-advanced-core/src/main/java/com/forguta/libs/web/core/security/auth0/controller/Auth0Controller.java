package com.forguta.libs.web.core.security.auth0.controller;

//@ConditionalOnExpression("${web-advanced.security.auth0.ssoEnable:true}")
//@Tag(name = "Authentication", description = "Authentication")
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/auth")
//public class Auth0Controller {
//
//    private final Auth0Service auth0Service;
//
//    @PostMapping("/signup")
//    public ResponseEntity<GenericResponseBody<SignupResponse>> signup(@RequestBody SignupRequest signupRequest) {
//        SignupResponse signup = auth0Service.signup(signupRequest);
//        return GenericResponseEntity.ok(signup);
//    }
//
//    @PostMapping("/authorize")
//    public ResponseEntity<GenericResponseBody<TokenResponse>> authorize(@RequestBody TokenRequest tokenRequest) {
//        TokenResponse tokenResponse = auth0Service.authorize(tokenRequest);
//        return GenericResponseEntity.ok(tokenResponse);
//    }
//
//    @PostMapping("/refresh")
//    public ResponseEntity<GenericResponseBody<TokenResponse>> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
//        TokenResponse tokenResponse = auth0Service.refresh(refreshTokenRequest);
//        return GenericResponseEntity.ok(tokenResponse);
//    }
//
//    @PostMapping("/logout")
//    public ResponseEntity<GenericResponseBody<Object>> logout() {
//        auth0Service.logout();
//        return GenericResponseEntity.ok("");
//    }
//
//}
