package com.jobportal.malawi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class MalawiLocationValidator implements ConstraintValidator<ValidMalawiLocation, String> {

    private final List<String> validLocations = Arrays.asList("Lilongwe", "Blantyre", "Mzuzu");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return validLocations.contains(value);
    }
}
