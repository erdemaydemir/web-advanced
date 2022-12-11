package com.forguta.libs.web.auth0.proxy;

import com.forguta.libs.web.auth0.proxy.config.Auth0ManagementAPIProxyConfig;
import com.forguta.libs.web.common.feign.APIClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "auth0managementapi", url = "${web-advanced.security.auth0.issuer}/api/v2", configuration = Auth0ManagementAPIProxyConfig.class)
public interface Auth0ManagementAPIClient extends APIClient {
}
