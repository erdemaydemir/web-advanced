package com.forguta.libs.web.core.security.config.properties;

import com.forguta.libs.web.core.security.eum.SecurityProviderEnum;
import com.forguta.libs.web.core.security.exception.SecurityConfigurationException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "web-advanced.security")
public class SecurityProperties {

    private boolean enabled;
    private SecurityProviderEnum provider;

    @PostConstruct
    public void validate() {
        boolean valid = true;

        if (enabled) {
            if (provider == null) {
                log.error("SecurityConfig Validation failure. Provider cannot be empty or null.");
                valid = false;
            }
        }

        if (valid) {
            log.info("SecurityConfig Validation done successfully. SecurityConfig = {{}}", this.toString());
        } else {
            throw new SecurityConfigurationException("Invalid SecurityConfig Configuration");
        }
    }

}
