package com.forguta.libs.web.auth0.service;

import com.forguta.libs.web.auth0.config.properties.Auth0Properties;
import com.forguta.libs.web.auth0.model.request.RefreshTokenRequest;
import com.forguta.libs.web.auth0.model.request.SignupRequest;
import com.forguta.libs.web.auth0.model.request.TokenRequest;
import com.forguta.libs.web.auth0.model.response.SignupResponse;
import com.forguta.libs.web.auth0.model.response.TokenResponse;
import com.forguta.libs.web.auth0.proxy.Auth0AuthenticationAPIProxy;
import com.forguta.libs.web.auth0.proxy.model.request.*;
import com.forguta.libs.web.auth0.proxy.model.response.Auth0SignupResponse;
import com.forguta.libs.web.auth0.proxy.model.response.Auth0TokenResponse;
import com.forguta.libs.web.common.event.LoggedInEvent;
import com.forguta.libs.web.common.event.LogoutEvent;
import com.forguta.libs.web.common.event.SingupEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@ConditionalOnExpression("${web-advanced.security.auth0.ssoEnable:true}")
@Service
@RequiredArgsConstructor
public class Auth0AuthenticationAPIService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final Auth0AuthenticationAPIProxy auth0AuthenticationAPIProxy;
    private final DozerBeanMapper dozerBeanMapper;
    private final Auth0Properties auth0Properties;

    private Auth0CommonRequest auth0AuthenticationCommonRequest;
    private Auth0CommonRequest auth0ManagementCommonRequest;

    @PostConstruct
    public void init() {
        auth0AuthenticationCommonRequest = Auth0CommonRequest.builder()
                .connection(auth0Properties.getConnectionName())
                .clientId(auth0Properties.getAuthenticationAPI().getClientId())
                .clientSecret(auth0Properties.getAuthenticationAPI().getClientSecret())
                .audience(auth0Properties.getAuthenticationAPI().getApiAudience()).build();

        auth0ManagementCommonRequest = Auth0CommonRequest.builder()
                .connection(auth0Properties.getConnectionName())
                .clientId(auth0Properties.getManagementAPI().getClientId())
                .clientSecret(auth0Properties.getManagementAPI().getClientSecret())
                .audience(auth0Properties.getManagementAPI().getApiAudience()).build();
    }

    public SignupResponse signup(SignupRequest signupRequest) {
        Auth0SignupRequest auth0SignupRequest = mapToProxyRequest(signupRequest, Auth0SignupRequest.class);
        Auth0SignupResponse auth0SignupResponse = auth0AuthenticationAPIProxy.signup(auth0SignupRequest);
        SignupResponse signupResponse = mapToResponse(auth0SignupResponse, SignupResponse.class);
        applicationEventPublisher.publishEvent(new SingupEvent(signupResponse));
        return signupResponse;
    }

    public TokenResponse authorize(TokenRequest tokenRequest) {
        Auth0TokenRequest auth0TokenRequest = mapToProxyRequest(tokenRequest, Auth0TokenRequest.class);
        Auth0TokenResponse auth0TokenResponse = auth0AuthenticationAPIProxy.authorize(auth0TokenRequest);
        TokenResponse tokenResponse = mapToResponse(auth0TokenResponse, TokenResponse.class);
        applicationEventPublisher.publishEvent(new LoggedInEvent(tokenResponse));
        return tokenResponse;
    }

    public TokenResponse clientAuthorize() {
        Auth0ClientTokenRequest auth0ClientTokenRequest = dozerBeanMapper.map(auth0ManagementCommonRequest, Auth0ClientTokenRequest.class);
        Auth0TokenResponse auth0TokenResponse = auth0AuthenticationAPIProxy.clientAuthorize(auth0ClientTokenRequest);
        return mapToResponse(auth0TokenResponse, TokenResponse.class);
    }

    public TokenResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        Auth0RefreshTokenRequest auth0RefreshTokenRequest = mapToProxyRequest(refreshTokenRequest, Auth0RefreshTokenRequest.class);
        Auth0TokenResponse auth0TokenResponse = auth0AuthenticationAPIProxy.refreshToken(auth0RefreshTokenRequest);
        return mapToResponse(auth0TokenResponse, TokenResponse.class);
    }

    public void logout() {
        Auth0LogoutRequest auth0LogoutRequest = dozerBeanMapper.map(auth0AuthenticationCommonRequest, Auth0LogoutRequest.class);
        auth0AuthenticationAPIProxy.logout(auth0LogoutRequest);
        applicationEventPublisher.publishEvent(new LogoutEvent(""));
    }

    private <Request, ProxyRequest extends Auth0CommonRequest> ProxyRequest mapToProxyRequest(Request request, Class<ProxyRequest> clazz) {
        ProxyRequest generatedProxyRequest = dozerBeanMapper.map(auth0AuthenticationCommonRequest, clazz);
        dozerBeanMapper.map(request, generatedProxyRequest);
        return generatedProxyRequest;
    }

    private <ProxyResponse, Response> Response mapToResponse(ProxyResponse pr, Class<Response> clazz) {
        return dozerBeanMapper.map(pr, clazz);
    }
}
