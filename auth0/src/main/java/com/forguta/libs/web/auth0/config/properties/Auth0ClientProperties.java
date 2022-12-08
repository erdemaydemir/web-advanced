package com.forguta.libs.web.auth0.config.properties;

import com.forguta.libs.web.common.exception.SecurityConfigurationException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;

@Data
@Slf4j
public class Auth0ClientProperties {

    private String apiAudience;
    private String clientId;
    private String clientSecret;

    @PostConstruct
    public void validate() {
        boolean valid = true;

        if (StringUtils.isEmpty(apiAudience)) {
            log.error("SagaAdvancedConfig Validation failure. apiAudience cannot be empty or null.");
            valid = false;
        }
        if (StringUtils.isEmpty(clientId)) {
            log.error("Auth0Config Validation failure. clientId cannot be empty or null.");
            valid = false;
        }
        if (StringUtils.isEmpty(clientSecret)) {
            log.error("Auth0Config Validation failure. clientSecret cannot be empty or null.");
            valid = false;
        }

        if (valid) {
            log.info("Auth0Config Validation done successfully. Auth0Config = {{}}", this.toString());
        } else {
            throw new SecurityConfigurationException("Invalid SecurityConfig Configuration");
        }
    }

}
