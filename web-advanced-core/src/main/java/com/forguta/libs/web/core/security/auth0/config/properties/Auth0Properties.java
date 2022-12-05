package com.forguta.libs.web.core.security.auth0.config.properties;

import com.forguta.libs.web.core.security.exception.SecurityConfigurationException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "web-advanced.security.auth0")
@ConditionalOnExpression("${web-advanced.security.enabled:true} and '${web-advanced.security.provider}'.equals('AUTH0')")
public class Auth0Properties {

    private boolean ssoEnable;
    private String apiAudience;
    private String issuer;
    private String clientId;
    private String clientSecret;
    private String connectionName;

    @PostConstruct
    public void validate() {
        boolean valid = true;

        if (StringUtils.isEmpty(apiAudience)) {
            log.error("SagaAdvancedConfig Validation failure. apiAudience cannot be empty or null.");
            valid = false;
        }

        if (StringUtils.isEmpty(issuer)) {
            log.error("Auth0Config Validation failure. issuer cannot be empty or null.");
            valid = false;
        }

        if (ssoEnable) {
            if (StringUtils.isEmpty(clientId)) {
                log.error("Auth0Config Validation failure. clientId cannot be empty or null.");
                valid = false;
            }
            if (StringUtils.isEmpty(clientSecret)) {
                log.error("Auth0Config Validation failure. clientSecret cannot be empty or null.");
                valid = false;
            }
            if (StringUtils.isEmpty(connectionName)) {
                log.error("Auth0Config Validation failure. connectionName cannot be empty or null.");
                valid = false;
            }
        }

        if (valid) {
            log.info("Auth0Config Validation done successfully. Auth0Config = {{}}", this.toString());
        } else {
            throw new SecurityConfigurationException("Invalid SecurityConfig Configuration");
        }
    }

}
