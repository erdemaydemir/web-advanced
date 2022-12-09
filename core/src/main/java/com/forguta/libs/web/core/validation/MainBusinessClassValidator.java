package com.forguta.libs.web.core.validation;

import com.forguta.libs.web.core.validation.annotation.BusinessClassValidation;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MainBusinessClassValidator implements ConstraintValidator<BusinessClassValidation, Object> {

    private List<BusinessClassValidator> businessClassValidators = new ArrayList<>();

    @Override
    public void initialize(BusinessClassValidation constraintAnnotation) {
        businessClassValidators = getBusinessClassValidatorsFromAnnotation(constraintAnnotation);
    }

    private List<BusinessClassValidator> getBusinessClassValidatorsFromAnnotation(BusinessClassValidation constraintAnnotation) {
        return Arrays.stream(constraintAnnotation.value())
                .map(businessValidatorClass -> {
                    try {
                        return businessValidatorClass.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    return new DefaultBusinessClassValidator();
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        List<BusinessClassValidator> businessClassValidators = this.businessClassValidators.stream()
                .filter(businessClassValidator -> !businessClassValidator.isValid(value))
                .collect(Collectors.toList());
        businessClassValidators.forEach(businessClassValidator -> {
            businessClassValidator.getNotValidMessages().forEach(o ->
                    context.buildConstraintViolationWithTemplate(o.toString())
                            .addConstraintViolation());
            businessClassValidator.getNotValidMessages().clear();
        });
        return businessClassValidators.isEmpty();
    }
}