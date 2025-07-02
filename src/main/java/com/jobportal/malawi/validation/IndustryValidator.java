package com.jobportal.malawi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class IndustryValidator implements ConstraintValidator<ValidIndustry, String> {

    private final List<String> validIndustries = Arrays.asList("IT", "Finance", "Healthcare");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return validIndustries.contains(value);
    }
}
