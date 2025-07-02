package com.jobportal.malawi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MalawiLocationValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMalawiLocation {
    String message() default "Invalid Malawi location";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
