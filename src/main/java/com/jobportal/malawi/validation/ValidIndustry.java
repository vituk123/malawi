package com.jobportal.malawi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IndustryValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIndustry {
    String message() default "Invalid industry";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
