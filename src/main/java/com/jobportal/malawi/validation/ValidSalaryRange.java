package com.jobportal.malawi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SalaryRangeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSalaryRange {
    String message() default "Invalid salary range";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
