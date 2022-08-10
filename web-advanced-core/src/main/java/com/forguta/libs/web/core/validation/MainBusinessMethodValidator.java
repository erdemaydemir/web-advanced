package com.forguta.libs.web.core.validation;

import com.forguta.libs.web.core.validation.annotation.BusinessMethodValidation;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class MainBusinessMethodValidator implements ConstraintValidator<BusinessMethodValidation, Object[]> {

    private List<BusinessMethodValidator> businessMethodValidators = new ArrayList<>();

    @Override
    public void initialize(BusinessMethodValidation constraintAnnotation) {
        businessMethodValidators = getBusinessMethodValidatorsFromAnnotation(constraintAnnotation);
    }

    private List<BusinessMethodValidator> getBusinessMethodValidatorsFromAnnotation(BusinessMethodValidation constraintAnnotation) {
        return Arrays.stream(constraintAnnotation.value())
                .map(businessValidatorClass -> {
                    try {
                        return businessValidatorClass.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    return new DefaultBusinessMethodValidator();
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(Object[] value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        List<BusinessMethodValidator> businessClassValidators = this.businessMethodValidators.stream()
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