package com.forguta.libs.web.core.config;

import com.forguta.libs.web.core.model.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Slf4j
@AllArgsConstructor
@Configuration
public class ApplicationEnvironment {

    private final ApplicationContext applicationContext;
    private static Environment ENVIRONMENT;

    @PostConstruct
    public void init() {
        setEnvironment(applicationContext.getEnvironment());
    }

    public static String getProperty(String key) {
        return ENVIRONMENT.getProperty(key);
    }

    public static String getApplicationName() {
        return getProperty(Constant.APPLICATION_NAME_KEY);
    }

    public static void setEnvironment(Environment environment) {
        ApplicationEnvironment.ENVIRONMENT = environment;
    }
}
