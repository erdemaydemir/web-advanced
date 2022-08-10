package com.forguta.libs.web.core.validation.annotation;


import com.forguta.libs.web.core.validation.BusinessClassValidator;
import com.forguta.libs.web.core.validation.MainBusinessClassValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = MainBusinessClassValidator.class)
@Documented
public @interface BusinessClassValidation {

    Class<? extends BusinessClassValidator>[] value() default {};

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
