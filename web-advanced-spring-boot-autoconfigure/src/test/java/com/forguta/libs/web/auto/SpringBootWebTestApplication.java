package com.forguta.libs.web.auto;

import com.forguta.libs.web.core.model.response.GenericResponseEntity;
import com.forguta.libs.web.core.model.response.body.GenericResponseBody;
import com.forguta.libs.web.core.security.config.AbstractEndpointSecurityAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringBootWebTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebTestApplication.class, args);
    }

    @RestController
    @RequestMapping("/api")
    public class Controller {

        @GetMapping("/hello")
        public ResponseEntity<GenericResponseBody<String>> hello() {
            return GenericResponseEntity.ok("hello world");
        }

        @GetMapping("/helloEx")
        public ResponseEntity<GenericResponseBody<String>> helloEx() {
            throw new RuntimeException("qswd");
        }
    }

    @Component
    public static class EndpointSecurityAware extends AbstractEndpointSecurityAware {

        @Override
        public void defineEndpoints(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.authorizeHttpRequests()
                    .mvcMatchers("/api/**").authenticated()
                    .anyRequest().permitAll().and();
        }

    }
}
