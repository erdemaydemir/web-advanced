package com.forguta.libs.web.core.validation.annotation;


import com.forguta.libs.web.core.validation.BusinessMethodValidator;
import com.forguta.libs.web.core.validation.MainBusinessMethodValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, CONSTRUCTOR})
@Retention(RUNTIME)
@Constraint(validatedBy = MainBusinessMethodValidator.class)
@Documented
public @interface BusinessMethodValidation {

    Class<? extends BusinessMethodValidator>[] value() default {};

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
